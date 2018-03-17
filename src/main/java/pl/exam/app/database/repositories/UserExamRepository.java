package pl.exam.app.database.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.exam.app.database.entities.components.UserExamKey;
import pl.exam.app.database.entities.jointables.UserExam;

import java.util.Collection;

public interface UserExamRepository extends CrudRepository<UserExam, UserExamKey>
{
	Collection<UserExam> findByKey_Exam_Id(Integer examId);

	UserExam findByKeyExamIdAndKeyUserNickname(Integer examId, String nickname);
}
