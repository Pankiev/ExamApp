package pl.exam.app.business.exam.control;

import org.springframework.stereotype.Service;
import pl.exam.app.business.authentication.control.UserDetails;
import pl.exam.app.business.exam.boundary.CreateExamRequest;
import pl.exam.app.business.exam.boundary.RequestQuestion;
import pl.exam.app.persistence.exam.Exam;
import pl.exam.app.persistence.exam.ExamRepository;
import pl.exam.app.persistence.question.Question;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public Collection<Exam> findAll(UserDetails userDetails) {
        return examRepository.findAll();
    }

    public Exam createExam(UserDetails userDetails, CreateExamRequest data) {
        Exam exam = new Exam();
        exam.setName(data.getName());
        exam.setQuestions(toEntityQuestions(exam, data.getQuestions()));
        return examRepository.save(exam);
    }

    private List<Question> toEntityQuestions(Exam exam, Collection<RequestQuestion> questions) {
        return questions.stream()
                .map(q -> toEntityQuestion(exam, q))
                .collect(Collectors.toList());
    }

    private Question toEntityQuestion(Exam exam, RequestQuestion data) {
        Question question = new Question();
        question.setAnswers(Collections.emptyList());
        question.setQuestion("Temp question");
        question.setSecondsForAnswer(60);
        question.setExam(exam);
        return question;
    }
}
