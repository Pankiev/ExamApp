package pl.exam.app.persistence.answer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    @Query("select answer.question.exam.id from Answer answer where answer.id=?1")
    Optional<Long> findExamId(Long answerId);
}
