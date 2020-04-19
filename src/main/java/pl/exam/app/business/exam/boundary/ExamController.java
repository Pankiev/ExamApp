package pl.exam.app.business.exam.boundary;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.exam.app.persistence.exam.Exam;
import pl.exam.app.persistence.question.Question;
import pl.exam.app.persistence.QuestionAnswer;
import pl.exam.app.persistence.user.User;
import pl.exam.app.persistence.userexam.UserExamKey;
import pl.exam.app.persistence.userexam.UserExam;
import pl.exam.app.persistence.exam.ExamRepository;
import pl.exam.app.persistence.userexam.UserExamRepository;
import pl.exam.app.persistence.user.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/exam")
public class ExamController {
    private final static Random random = new Random();
    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final UserExamRepository userExamRepository;

    public ExamController(UserRepository userRepository, ExamRepository examRepository, UserExamRepository userExamRepository) {
        this.userRepository = userRepository;
        this.examRepository = examRepository;
        this.userExamRepository = userExamRepository;
    }

    @GetMapping({"/", "/index"})
    public String examIndex(SecurityContextHolderAwareRequestWrapper authentication) {
        if (authentication.isUserInRole("admin"))
            return "exam/admin-index";
        if (authentication.isUserInRole("student"))
            return "exam/student-index";
        return "denied/index";
    }

    @GetMapping("/create")
    public String examCreate() {
        return "exam/create";
    }

    @GetMapping("/{id}")
    public String examShow(@PathVariable("id") Integer examId, ModelMap model,
                           SecurityContextHolderAwareRequestWrapper authentication) {
        Optional<Exam> exam = examRepository.findById(examId);
        if (exam.isEmpty())
            return "404/index";
        model.addAttribute("exam", exam.get());
        if (authentication.isUserInRole("admin"))
            return "exam/admin-show";
        if (authentication.isUserInRole("student"))
            return examStudentView(examId, authentication);

        return "denied/index";
    }

    private String examStudentView(@PathVariable("id") Integer examId,
                                   SecurityContextHolderAwareRequestWrapper authentication) {
        UserExam userExam = userExamRepository
                .findByKeyExamIdAndKeyUserUsername(examId, authentication.getRemoteUser());
        if (testHasFinished(userExam))
            return "exam/result-student";
        if (testHasBeenOpened(userExam))
            return "exam/take-test";
        return "exam/student-show";
    }

    @GetMapping("/{id}/result")
    @Secured("ROLE_admin")
    public String examResult(@PathVariable("id") Integer examId, ModelMap model) {
        Optional<Exam> exam = examRepository.findById(examId);
        if (exam.isEmpty())
            return "404/index";
        model.addAttribute("exam", exam.get());
        return "exam/result-admin";
    }

    @GetMapping("/{id}/takeTest")
    public String takeTest(@PathVariable("id") Integer examId, ModelMap model,
                           SecurityContextHolderAwareRequestWrapper authentication) {
        Optional<Exam> exam = examRepository.findById(examId);
        if (exam.isEmpty())
            return "404/index";
        UserExam userExam = userExamRepository
                .findByKeyExamIdAndKeyUserUsername(examId, authentication.getRemoteUser());
        if (testHasFinished(userExam))
            return "redirect:/exam/" + examId;
        model.addAttribute("exam", exam.get());
        if (userExam.getTestApproachDate() == null)
            prepareForFirstTimeVisit(authentication, exam.get());

        return "exam/take-test";
    }

    private void prepareForFirstTimeVisit(SecurityContextHolderAwareRequestWrapper authentication, Exam exam) {
        UserExam newUserExam = createUserExamEvent(exam, authentication);
        userExamRepository.save(newUserExam);
    }

    private boolean testHasBeenOpened(UserExam userExam) {
        return userExam != null && userExam.getTestApproachDate() != null;
    }

    private boolean testHasFinished(UserExam userExam) {
        return userExam != null && userExam.getFinished();
    }

    private QuestionAnswer toQuestionAnswer(Question question) {
        QuestionAnswer questionAnswer = new QuestionAnswer();
        questionAnswer.setQuestion(question);
        return questionAnswer;
    }

    private UserExam createUserExamEvent(Exam exam, SecurityContextHolderAwareRequestWrapper authentication) {
        String username = authentication.getRemoteUser();
        User user = userRepository.findByUsername(username).orElseThrow();
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

    private Set<Question> drawRandomQuestions(Exam exam) {
        Set<Question> randomQuestions = new HashSet<>();
        List<Question> questions = exam.getQuestions();
        while (!questions.isEmpty() && randomQuestions.size() < 10)
            randomQuestions.add(questions.remove(random.nextInt(questions.size())));
        return randomQuestions;
    }
}
