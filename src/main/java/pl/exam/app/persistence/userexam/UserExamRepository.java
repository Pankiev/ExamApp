package pl.exam.app.persistence.userexam;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.exam.app.persistence.userexam.UserExamKey;
import pl.exam.app.persistence.userexam.UserExam;

import java.util.Collection;
import java.util.Optional;

public interface UserExamRepository extends CrudRepository<UserExam, UserExamKey> {
    Collection<UserExam> findByKeyExamId(Long examId);

    Optional<UserExam> findByKeyExamIdAndKeyUserUsername(Long examId, String username);

    @Query("SELECT count(qa) FROM QuestionAnswer qa " +
            "WHERE qa.key.userExam=?1 " +
            "AND qa.key.answer.valid=true")
    int findTotalScore(UserExam userExam);
}
