package pl.exam.app.examapp.database.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.exam.app.examapp.database.entities.Question;

public interface QuestionRepository extends CrudRepository<Question, Integer>
{

}
