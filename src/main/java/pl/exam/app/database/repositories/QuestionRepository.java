package pl.exam.app.database.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.exam.app.database.entities.Question;

import java.util.Set;

public interface QuestionRepository extends CrudRepository<Question, Integer> {
    Set<Question> findByExamId(Integer examId);
}
