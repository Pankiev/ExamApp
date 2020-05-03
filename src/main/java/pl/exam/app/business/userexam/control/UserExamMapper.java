package pl.exam.app.business.userexam.control;

import org.apache.catalina.User;
import pl.exam.app.business.exam.boundary.RestUserExamData;
import pl.exam.app.business.exam.control.ExamMapper;
import pl.exam.app.business.questionanswer.control.QuestionAnswerMapper;
import pl.exam.app.business.user.control.UserMapper;
import pl.exam.app.persistence.userexam.UserExam;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserExamMapper {

    private final ExamMapper examMapper = new ExamMapper();
    private final QuestionAnswerMapper questionAnswerMapper = new QuestionAnswerMapper();
    private final UserMapper userMapper = new UserMapper();

    public Collection<RestUserExamData> toUserApproachesRestData(Collection<UserExam> userExams) {
        return userExams.stream()
                .map(this::toUserApproachesRestData)
                .collect(Collectors.toList());
    }

    public RestUserExamData toUserApproachesRestData(UserExam userExam) {
        return restDataWithoutExamBuilder(userExam)
                .build();
    }

    public RestUserExamData toRestData(UserExam userExam) {
        return restDataWithoutExamBuilder(userExam)
                .exam(examMapper.toRestData(userExam.getExam()))
                .build();
    }

    private RestUserExamData.RestUserExamDataBuilder restDataWithoutExamBuilder(UserExam userExam) {
        return RestUserExamData.builder()
                .user(userMapper.toRestData(userExam.getUser()))
                .assignmentDate(userExam.getAssignmentDate())
                .finished(userExam.isFinished())
                .questionsWithAnswers(questionAnswerMapper.toRestData(userExam.getQuestionsWithAnswers()))
                .testApproachDate(userExam.getTestApproachDate())
                .totalScore(userExam.getTotalScore());
    }

}
