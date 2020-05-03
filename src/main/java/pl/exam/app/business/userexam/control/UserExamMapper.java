package pl.exam.app.business.userexam.control;

import pl.exam.app.business.exam.boundary.RestUserExamData;
import pl.exam.app.business.exam.control.ExamMapper;
import pl.exam.app.business.questionanswer.control.QuestionAnswerMapper;
import pl.exam.app.persistence.userexam.UserExam;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserExamMapper {

    private final ExamMapper examMapper = new ExamMapper();
    private final QuestionAnswerMapper questionAnswerMapper = new QuestionAnswerMapper();

    public Collection<RestUserExamData> toRestData(Collection<UserExam> userExams) {
        return userExams.stream()
                .map(this::toRestData)
                .collect(Collectors.toList());
    }

    public RestUserExamData toRestData(UserExam userExam) {
        return RestUserExamData.builder()
                .assignmentDate(userExam.getAssignmentDate())
                .finished(userExam.isFinished())
                .questionsWithAnswers(questionAnswerMapper.toRestData(userExam.getQuestionsWithAnswers()))
                .testApproachDate(userExam.getTestApproachDate())
                .totalScore(userExam.getTotalScore())
                .exam(examMapper.toRestData(userExam.getExam()))
                .build();
    }

}
