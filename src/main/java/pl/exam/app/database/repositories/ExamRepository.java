package pl.exam.app.database.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.exam.app.database.entities.Exam;

public interface ExamRepository extends CrudRepository<Exam, Integer>
{

}
