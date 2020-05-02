package pl.exam.app.persistence.userexam;

import org.springframework.data.repository.CrudRepository;
import pl.exam.app.persistence.userexam.UserExamKey;
import pl.exam.app.persistence.userexam.UserExam;

import java.util.Collection;
import java.util.Optional;

public interface UserExamRepository extends CrudRepository<UserExam, UserExamKey> {
    Collection<UserExam> findByKey_Exam_Id(Long examId);

    Optional<UserExam> findByKeyExamIdAndKeyUserUsername(Long examId, String username);
}
