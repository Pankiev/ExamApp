package pl.exam.app.database.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.exam.app.database.entities.User;

public interface UserRepository extends CrudRepository<User, Integer>
{
	User findByNickname(String nickname);
}
