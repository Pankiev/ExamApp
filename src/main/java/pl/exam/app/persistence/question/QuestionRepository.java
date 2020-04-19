package pl.exam.app.persistence.question;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface QuestionRepository extends CrudRepository<Question, Integer> {
    Set<Question> findByExamId(Integer examId);
}
