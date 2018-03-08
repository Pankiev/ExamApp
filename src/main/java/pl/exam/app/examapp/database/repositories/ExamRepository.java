package pl.exam.app.examapp.database.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.exam.app.examapp.database.entities.Exam;

public interface ExamRepository extends CrudRepository<Exam, Integer>
{

}
