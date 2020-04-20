package pl.exam.app.business.answer.control;

import pl.exam.app.business.exam.boundary.RestAnswerData;
import pl.exam.app.persistence.Answer;
import pl.exam.app.persistence.question.Question;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AnswerMapper {

    public Collection<RestAnswerData> toRestData(Collection<Answer> answers) {
        return answers.stream()
                .map(this::toRestData)
                .collect(Collectors.toList());
    }

    public RestAnswerData toRestData(Answer answer) {
        return RestAnswerData.builder()
                .id(answer.getId())
                .valid(answer.isValid())
                .answer(answer.getAnswer())
                .build();
    }

    public List<Answer> toEntities(Collection<RestAnswerData> data, Question question) {
        return data.stream()
                .map(element -> toEntity(element, question))
                .collect(Collectors.toList());
    }

    public Answer toEntity(RestAnswerData data, Question question) {
        Answer answer = new Answer();
        answer.setId(data.getId());
        answer.setValid(data.isValid());
        answer.setAnswer(data.getAnswer());
        answer.setQuestion(question);
        return answer;
    }
}
