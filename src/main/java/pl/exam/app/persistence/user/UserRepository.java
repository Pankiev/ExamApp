package pl.exam.app.persistence.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.exam.app.persistence.exam.Exam;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT u FROM User u, in (u.roles)role WHERE u.idInClass = null OR u.schoolClass = null")
    Iterable<User> getUsersWithoutClass();

    @Query("SELECT DISTINCT u.schoolClass FROM User u WHERE u.schoolClass <> null")
    Iterable<String> findDistinctSchoolClasses();

    Collection<User> findBySchoolClass(String schoolClass);

    Collection<User> findByExams_Key_Exam(Exam exam);
}
