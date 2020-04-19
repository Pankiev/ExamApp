package pl.exam.app.persistence.exam;

import org.springframework.data.repository.CrudRepository;
import pl.exam.app.persistence.exam.Exam;

import java.util.Collection;

public interface ExamRepository extends CrudRepository<Exam, Integer> {
    Collection<Exam> findByUsers_Key_User_Username(String username);

    Collection<Exam> findAll();
}
