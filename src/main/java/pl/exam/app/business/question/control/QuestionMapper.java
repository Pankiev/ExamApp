package pl.exam.app.business.question.control;

import pl.exam.app.business.answer.control.AnswerMapper;
import pl.exam.app.business.exam.boundary.RestQuestionData;
import pl.exam.app.persistence.exam.Exam;
import pl.exam.app.persistence.question.Question;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionMapper {
    private final AnswerMapper answerMapper = new AnswerMapper();

    public Collection<RestQuestionData> toRestData(Collection<Question> questions) {
        return questions.stream()
                .map(this::toRestData)
                .collect(Collectors.toList());
    }

    public RestQuestionData toRestData(Question question) {
        return RestQuestionData.builder()
                .id(question.getId())
                .question(question.getQuestion())
                .secondsForAnswer(question.getSecondsForAnswer())
                .answers(answerMapper.toRestData(question.getAnswers()))
                .build();
    }

    public List<Question> toEntities(Collection<RestQuestionData> questions, Exam exam) {
        return questions.stream()
                .map(data -> toEntity(data, exam))
                .collect(Collectors.toList());
    }

    public Question toEntity(RestQuestionData data, Exam exam) {
        Question question = new Question();
        question.setId(data.getId());
        question.setSecondsForAnswer(data.getSecondsForAnswer());
        question.setQuestion(data.getQuestion());
        question.setAnswers(answerMapper.toEntities(data.getAnswers(), question));
        question.setExam(exam);
        return question;
    }
}
