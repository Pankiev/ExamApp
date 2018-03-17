package pl.exam.app.database.repositories;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.exam.app.database.entities.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer>
{
	User findByNickname(String nickname);

	@Query("SELECT u FROM User u, in (u.roles)role WHERE u.idInClass = null OR u.schoolClass = null")
	Iterable<User> getUsersWithoutClass();

	@Query("SELECT DISTINCT u.schoolClass FROM User u WHERE u.schoolClass <> null")
	Iterable<String> findDistinctSchoolClasses();
}
