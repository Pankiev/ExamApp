package pl.exam.app.business.exam.control;

import org.springframework.stereotype.Service;
import pl.exam.app.business.authentication.control.UserDetails;
import pl.exam.app.business.exam.boundary.RestExamData;
import pl.exam.app.business.exam.boundary.RestAnswerData;
import pl.exam.app.business.exam.boundary.RestQuestionData;
import pl.exam.app.persistence.Answer;
import pl.exam.app.persistence.exam.Exam;
import pl.exam.app.persistence.exam.ExamRepository;
import pl.exam.app.persistence.question.Question;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExamService {

    private final ExamMapper examMapper = new ExamMapper();
    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
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

    private Question toEntityQuestion(Exam exam, RestQuestionData data) {
        Question question = new Question();
        question.setAnswers(toEntityAnswers(question, data.getAnswers()));
        question.setQuestion(data.getQuestion());
        question.setSecondsForAnswer(data.getSecondsForAnswer());
        question.setExam(exam);
        return question;
    }

    private List<Answer> toEntityAnswers(Question question, Collection<RestAnswerData> anwsers) {
        return anwsers.stream()
                .map(answer -> toEntityAnswer(question, answer))
                .collect(Collectors.toList());
    }

    private Answer toEntityAnswer(Question question, RestAnswerData data) {
        Answer answer = new Answer();
        answer.setAnswer(data.getAnswer());
        answer.setQuestion(question);
        answer.setValid(data.isValid());
        return answer;
    }
}
