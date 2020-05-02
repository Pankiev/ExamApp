package pl.exam.app.persistence.questionanswer;

import org.springframework.data.repository.CrudRepository;

public interface QuestionAnswerRepository extends CrudRepository<QuestionAnswer, QuestionAnswerKey> {
}
