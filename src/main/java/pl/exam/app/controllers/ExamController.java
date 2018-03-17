package pl.exam.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.exam.app.database.entities.Exam;
import pl.exam.app.database.entities.Question;
import pl.exam.app.database.entities.QuestionAnswer;
import pl.exam.app.database.entities.User;
import pl.exam.app.database.entities.components.UserExamKey;
import pl.exam.app.database.entities.jointables.UserExam;
import pl.exam.app.database.repositories.ExamRepository;
import pl.exam.app.database.repositories.UserExamRepository;
import pl.exam.app.database.repositories.UserRepository;
import pl.exam.app.exceptions.UserExamAlreadyExistsException;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/exam")
public class ExamController
{
	private final static Random random = new Random();
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ExamRepository examRepository;
	@Autowired
	private UserExamRepository userExamRepository;

	@GetMapping({ "/", "/index" })
	public String examIndex(SecurityContextHolderAwareRequestWrapper authentication)
	{
		if (authentication.isUserInRole("admin"))
			return "exam/admin-index";
		if (authentication.isUserInRole("student"))
			return "exam/student-index";
		return "denied/index";
	}

	@GetMapping("/create")
	public String examCreate()
	{
		return "exam/create";
	}

	@GetMapping("/{id}")
	public String examShow(@PathVariable("id") Integer examId, ModelMap model,
			SecurityContextHolderAwareRequestWrapper authentication)
	{
		Optional<Exam> exam = examRepository.findById(examId);
		if (!exam.isPresent())
			return "404/index";
		model.addAttribute("exam", exam.get());
		if (authentication.isUserInRole("admin"))
			return "exam/admin-show";
		if (authentication.isUserInRole("student"))
			return "exam/student-show";
		return "denied/index";
	}

	@GetMapping("/{id}/result")
	public String examResult(@PathVariable("id") Integer examId, ModelMap model)
	{
		Optional<Exam> exam = examRepository.findById(examId);
		if (!exam.isPresent())
			return "404/index";
		model.addAttribute("exam", exam.get());
		return "exam/result";
	}

	@GetMapping("/{id}/takeTest")
	public String takeTest(@PathVariable("id") Integer examId, ModelMap model,
			SecurityContextHolderAwareRequestWrapper authentication)
	{
		Optional<Exam> exam = examRepository.findById(examId);
		if (!exam.isPresent())
			return "404/index";
		model.addAttribute("exam", exam.get());
		updateUserExamEvent(exam.get(), authentication);
		return "exam/take-test";
	}

	private QuestionAnswer toQuestionAnswer(Question question)
	{
		QuestionAnswer questionAnswer = new QuestionAnswer();
		questionAnswer.setQuestion(question);
		return questionAnswer;
	}

	private void updateUserExamEvent(Exam exam, SecurityContextHolderAwareRequestWrapper authentication)
	{
		String nickname = authentication.getRemoteUser();
		User user = userRepository.findByNickname(nickname);
		UserExamKey userExamKey = new UserExamKey(user, exam);
		Set<Question> randomQuestions = drawRandomQuestions(exam);
		Set<QuestionAnswer> randomQuestionAnswers = randomQuestions.stream()
				.map(this::toQuestionAnswer)
				.collect(Collectors.toSet());

		UserExam userExam = userExamRepository.findById(userExamKey).get();
		userExam.setQuestionsWithAnswers(randomQuestionAnswers);
		userExamRepository.save(userExam);
	}

	private Set<Question> drawRandomQuestions(Exam exam)
	{
		Set<Question> randomQuestions = new HashSet<>();
		List<Question> questions = exam.getQuestions();
		while (!questions.isEmpty() && randomQuestions.size() < 10)
			randomQuestions.add(questions.remove(random.nextInt(questions.size())));
		return randomQuestions;
	}
}
