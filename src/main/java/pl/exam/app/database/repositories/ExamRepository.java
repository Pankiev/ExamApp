package pl.exam.app.database.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.exam.app.database.entities.Exam;

import java.util.Collection;

public interface ExamRepository extends CrudRepository<Exam, Integer>
{
	Collection<Exam> findByUsers_Key_User_Nickname(String nickname);
}
