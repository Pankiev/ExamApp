package pl.exam.app.business.exam.control;

import org.springframework.stereotype.Service;
import pl.exam.app.business.authentication.control.UserDetails;
import pl.exam.app.business.exam.boundary.RestExamData;
import pl.exam.app.business.exam.boundary.RestUserExamData;
import pl.exam.app.business.exam.control.exception.ExamNotFoundException;
import pl.exam.app.business.exam.control.exception.TakeExamException;
import pl.exam.app.business.userexam.control.UserExamMapper;
import pl.exam.app.persistence.exam.Exam;
import pl.exam.app.persistence.exam.ExamRepository;
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

    public ExamService(ExamRepository examRepository, UserRepository userRepository, UserExamRepository userExamRepository) {
        this.examRepository = examRepository;
        this.userRepository = userRepository;
        this.userExamRepository = userExamRepository;
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
}
