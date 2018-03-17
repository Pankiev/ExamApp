package pl.exam.app.database.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.exam.app.database.entities.components.UserExamKey;
import pl.exam.app.database.entities.jointables.UserExam;

public interface UserExamRepository extends CrudRepository<UserExam, UserExamKey>
{
}
