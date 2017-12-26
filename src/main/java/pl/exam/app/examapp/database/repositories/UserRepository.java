package pl.exam.app.examapp.database.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.exam.app.examapp.database.entities.User;

public interface UserRepository extends CrudRepository<User, Integer>
{
	User findByNickname(String nickname);
}
