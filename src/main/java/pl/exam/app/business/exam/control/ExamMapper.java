package pl.exam.app.business.exam.control;

import pl.exam.app.business.exam.boundary.RestExamData;
import pl.exam.app.business.question.control.QuestionMapper;
import pl.exam.app.persistence.exam.Exam;

import java.util.Collection;
import java.util.stream.Collectors;

public class ExamMapper {
    private final QuestionMapper questionMapper = new QuestionMapper();

    public Collection<RestExamData> toRestData(Collection<Exam> exams) {
        return exams.stream()
                .map(this::toRestData)
                .collect(Collectors.toList());
    }

    public RestExamData toRestData(Exam exam) {
        return RestExamData.builder()
                .id(exam.getId())
                .name(exam.getName())
                .questions(questionMapper.toRestData(exam.getQuestions()))
                .build();
    }

    public Exam toEntity(RestExamData data) {
        Exam exam = new Exam();
        exam.setId(data.getId());
        exam.setName(data.getName());
        exam.setQuestions(questionMapper.toEntities(data.getQuestions(), exam));
        return exam;
    }
}
