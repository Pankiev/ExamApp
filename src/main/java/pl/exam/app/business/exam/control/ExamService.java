package pl.exam.app.business.exam.control;

import org.springframework.stereotype.Service;
import pl.exam.app.business.authentication.control.UserDetails;
import pl.exam.app.business.exam.boundary.RestExamData;
import pl.exam.app.business.exam.boundary.RestUserExamData;
import pl.exam.app.business.exam.control.exception.AnswerNotFoundException;
import pl.exam.app.business.exam.control.exception.ExamNotFoundException;
import pl.exam.app.business.exam.control.exception.TakeExamException;
import pl.exam.app.business.exam.control.exception.UserExamNotFoundException;
import pl.exam.app.business.userexam.control.UserExamMapper;
import pl.exam.app.persistence.answer.Answer;
import pl.exam.app.persistence.answer.AnswerRepository;
import pl.exam.app.persistence.exam.Exam;
import pl.exam.app.persistence.exam.ExamRepository;
import pl.exam.app.persistence.question.Question;
import pl.exam.app.persistence.questionanswer.QuestionAnswer;
import pl.exam.app.persistence.questionanswer.QuestionAnswerRepository;
import pl.exam.app.persistence.user.User;
import pl.exam.app.persistence.user.UserRepository;
import pl.exam.app.persistence.userexam.UserExam;
import pl.exam.app.persistence.userexam.UserExamRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class ExamService {
    private final UserExamMapper userExamMapper = new UserExamMapper();
    private final ExamMapper examMapper = new ExamMapper();
    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final UserExamRepository userExamRepository;
    private final AnswerRepository answerRepository;
    private final QuestionAnswerRepository questionAnswerRepository;

    public ExamService(ExamRepository examRepository, UserRepository userRepository,
                       UserExamRepository userExamRepository, AnswerRepository answerRepository,
                       QuestionAnswerRepository questionAnswerRepository) {
        this.examRepository = examRepository;
        this.userRepository = userRepository;
        this.userExamRepository = userExamRepository;
        this.answerRepository = answerRepository;
        this.questionAnswerRepository = questionAnswerRepository;
    }

    public Collection<RestExamData> findAll(UserDetails userDetails) {
        Collection<Exam> exams = examRepository.findAll();
        return examMapper.toRestData(exams);
    }

    public RestExamData createExam(UserDetails userDetails, RestExamData data) {
        Exam exam = examMapper.toEntity(data);
        Exam savedExam = examRepository.save(exam);
        return examMapper.toRestData(savedExam);
    }

    public RestUserExamData takeTest(UserDetails userDetails, Long examId) {
        validateForTakingTest(userDetails, examId);
        UserExam userExam = doTakeTest(userDetails, examId);
        return userExamMapper.toRestData(userExam);
    }

    public void chooseActiveTestAnswer(UserDetails userDetails, Long answerId) {
        activeTestUserQuestionChange(userDetails, answerId, this::chooseUserAnswer);
    }

    private void chooseUserAnswer(UserExam userExam, Question question, Answer answer) {
        questionAnswerRepository.save(new QuestionAnswer(userExam, question, answer));
    }

    private void activeTestUserQuestionChange(UserDetails userDetails, Long answerId, ActiveTestUserQuestionAction action) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException(answerId));
        Question question = answer.getQuestion();
        Long examId = question.getExam().getId();
        UserExam userExam = userExamRepository.findByKeyExamIdAndKeyUserUsername(examId, userDetails.getUsername())
                .orElseThrow(() -> new TakeExamException("Test id " + examId + " has not been started for user " + userDetails.getUsername()));
        if (userExam.isFinished()) {
            throw new TakeExamException("Exam has been already taken");
        }
        action.apply(userExam, question, answer);
    }

    public void unchooseActiveTestAnswer(UserDetails userDetails, Long answerId) {
        activeTestUserQuestionChange(userDetails, answerId, this::unchooseUserAnswer);
    }

    private void unchooseUserAnswer(UserExam userExam, Question question, Answer answer) {
        questionAnswerRepository.delete(new QuestionAnswer(userExam, question, answer));
    }

    public RestUserExamData submitTest(UserDetails userDetails, Long examId) {
        UserExam userExam = userExamRepository.findByKeyExamIdAndKeyUserUsername(examId, userDetails.getUsername())
                .orElseThrow(() -> new TakeExamException("Test id " + examId + " has not been started for user " + userDetails.getUsername()));
        if (userExam.isFinished()) {
            throw new TakeExamException("Exam has been already taken");
        }
        int totalScore = userExamRepository.findTotalScore(userExam);
        userExam.setTotalScore((float) totalScore);
        userExam.setFinished(true);
        return userExamMapper.toRestData(userExam);
    }

    private void validateForTakingTest(UserDetails userDetails, Long examId) {
        examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException(examId));
        Optional<UserExam> userExam = userExamRepository.findByKeyExamIdAndKeyUserUsername(examId, userDetails.getUsername());
        if (userExam.isPresent() && userExam.get().isFinished()) {
            throw new TakeExamException("Exam has been already taken");
        }
    }

    private UserExam doTakeTest(UserDetails userDetails, Long examId) {
        Optional<UserExam> existingUserExam = userExamRepository.findByKeyExamIdAndKeyUserUsername(examId, userDetails.getUsername());
        return existingUserExam
                .orElseGet(() -> createNewUserExam(userDetails.getUsername(), examId));
    }

    private UserExam createNewUserExam(String username, Long examId) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Exam exam = examRepository.findById(examId).orElseThrow();
        UserExam userExam = newUserExam(exam, user);
        return userExamRepository.save(userExam);
    }

    private UserExam newUserExam(Exam exam, User user) {
        UserExam userExam = new UserExam(user, exam);
        userExam.setTestApproachDate(new Date());
        return userExam;
    }

    public RestUserExamData getUserExamResultDetails(UserDetails userDetails, Long examId) {
        UserExam userExam = userExamRepository.findByKeyExamIdAndKeyUserUsername(examId, userDetails.getUsername())
                .orElseThrow(() -> new UserExamNotFoundException(userDetails.getUsername(), examId));
        if (!userExam.isFinished()) {
            throw new TakeExamException("Exam is not finished yet");
        }
        return userExamMapper.toRestData(userExam);
    }

    public Collection<RestUserExamData> getExamApproaches(Long examId) {
        Collection<UserExam> userExams = userExamRepository.findByKeyExamId(examId);
        return userExamMapper.toRestData(userExams);
    }
}
