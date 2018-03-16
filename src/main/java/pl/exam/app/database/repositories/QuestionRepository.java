package pl.exam.app.database.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.exam.app.database.entities.Question;

public interface QuestionRepository extends CrudRepository<Question, Integer>
{

}
