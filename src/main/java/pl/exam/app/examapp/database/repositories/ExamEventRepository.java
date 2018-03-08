package pl.exam.app.examapp.database.repositories;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import pl.exam.app.examapp.database.entities.ExamEvent;

public interface ExamEventRepository extends CrudRepository<ExamEvent, Integer> 
{
	Collection<ExamEvent> findByOpenedTrueOrderByCreationDateDesc();
	
	Collection<ExamEvent> findByOpenedFalseOrderByCreationDateDesc();
	
	Collection<ExamEvent> findAllByOrderByCreationDateDesc();
}
