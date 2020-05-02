package pl.exam.app.business.questionanswer.control;

import pl.exam.app.business.answer.control.AnswerMapper;
import pl.exam.app.business.question.control.QuestionMapper;
import pl.exam.app.business.questionanswer.boundary.RestQuestionAnswerData;
import pl.exam.app.persistence.QuestionAnswer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestionAnswerMapper {

    private final AnswerMapper answerMapper = new AnswerMapper();
    private final QuestionMapper questionMapper = new QuestionMapper();

    public List<RestQuestionAnswerData> toRestData(Set<QuestionAnswer> questionsWithAnswers) {
        return questionsWithAnswers.stream()
                .map(this::toRestData)
                .collect(Collectors.toList());
    }

    private RestQuestionAnswerData toRestData(QuestionAnswer questionsWithAnswers) {
        return RestQuestionAnswerData.builder()
                .id(questionsWithAnswers.getId())
                .answer(answerMapper.toRestData(questionsWithAnswers.getAnswer()))
                .question(questionMapper.toRestData(questionsWithAnswers.getQuestion()))
                .build();
    }
}
