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

import javax.annotation.Nullable;
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
			return examStudentView(examId, authentication);

		return "denied/index";
	}

	private String examStudentView(@PathVariable("id") Integer examId,
			SecurityContextHolderAwareRequestWrapper authentication)
	{
		if(!testHasBeenTaken(examId, authentication.getRemoteUser()))
			return "exam/student-show";
		else
			return "exam/result-student";
	}

	@GetMapping("/{id}/result")
	public String examResult(@PathVariable("id") Integer examId, ModelMap model)
	{
		Optional<Exam> exam = examRepository.findById(examId);
		if (!exam.isPresent())
			return "404/index";
		model.addAttribute("exam", exam.get());
		return "exam/result-admin";
	}

	@GetMapping("/{id}/takeTest")
	public String takeTest(@PathVariable("id") Integer examId, ModelMap model,
			SecurityContextHolderAwareRequestWrapper authentication)
	{
		Optional<Exam> exam = examRepository.findById(examId);
		if (!exam.isPresent())
			return "404/index";
		UserExam userExam = userExamRepository
				.findByKeyExamIdAndKeyUserNickname(examId, authentication.getRemoteUser());
		if(testHasBeenTaken(userExam))
			return "redirect:/exam/" + examId;
		model.addAttribute("exam", exam.get());
		if(userExam.getTestApproachDate() == null)
			prepareForFirstTimeVisit(authentication, exam.get());

		return "exam/take-test";
	}

	private void prepareForFirstTimeVisit(SecurityContextHolderAwareRequestWrapper authentication, Exam exam)
	{
		UserExam newUserExam = createUserExamEvent(exam, authentication);
		userExamRepository.save(newUserExam);
	}

	private Boolean testHasBeenTaken(Integer examId, String username)
	{
		UserExam userExam = userExamRepository
				.findByKeyExamIdAndKeyUserNickname(examId, username);
		return testHasBeenTaken(userExam);
	}

	private Boolean testHasBeenTaken(@Nullable UserExam userExam)
	{
		return userExam != null && userExam.getFinished();
	}

	private QuestionAnswer toQuestionAnswer(Question question)
	{
		QuestionAnswer questionAnswer = new QuestionAnswer();
		questionAnswer.setQuestion(question);
		return questionAnswer;
	}

	private UserExam createUserExamEvent(Exam exam, SecurityContextHolderAwareRequestWrapper authentication)
	{
		String nickname = authentication.getRemoteUser();
		User user = userRepository.findByNickname(nickname);
		UserExamKey userExamKey = new UserExamKey(user, exam);
		Set<Question> randomQuestions = drawRandomQuestions(exam);
		Set<QuestionAnswer> randomQuestionAnswers = randomQuestions.stream()
				.map(this::toQuestionAnswer)
				.collect(Collectors.toSet());

		UserExam userExam = userExamRepository.findById(userExamKey).get();
		randomQuestionAnswers.forEach(questionAnswer -> questionAnswer.setUserExam(userExam));
		userExam.setQuestionsWithAnswers(randomQuestionAnswers);
		userExam.setTestApproachDate(new Date());
		return userExam;
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
