package pl.exam.app.persistence.userexam;

import org.springframework.data.repository.CrudRepository;
import pl.exam.app.persistence.userexam.UserExamKey;
import pl.exam.app.persistence.userexam.UserExam;

import java.util.Collection;

public interface UserExamRepository extends CrudRepository<UserExam, UserExamKey> {
    Collection<UserExam> findByKey_Exam_Id(Integer examId);

    UserExam findByKeyExamIdAndKeyUserNickname(Integer examId, String nickname);
}
