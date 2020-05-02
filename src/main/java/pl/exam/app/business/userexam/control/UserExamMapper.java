package pl.exam.app.business.userexam.control;

import pl.exam.app.business.exam.boundary.RestUserExamData;
import pl.exam.app.business.exam.control.ExamMapper;
import pl.exam.app.business.questionanswer.control.QuestionAnswerMapper;
import pl.exam.app.persistence.userexam.UserExam;

public class UserExamMapper {

    private final ExamMapper examMapper = new ExamMapper();
    private final QuestionAnswerMapper questionAnswerMapper = new QuestionAnswerMapper();

    public RestUserExamData toRestData(UserExam userExam) {
        return RestUserExamData.builder()
                .assignmentDate(userExam.getAssignmentDate())
                .finished(userExam.isFinished())
                .questionsWithAnswers(questionAnswerMapper.toRestData(userExam.getQuestionsWithAnswers()))
                .exam(examMapper.toRestData(userExam.getExam()))
                .testApproachDate(userExam.getTestApproachDate())
                .totalScore(userExam.getTotalScore())
                .build();
    }
}
