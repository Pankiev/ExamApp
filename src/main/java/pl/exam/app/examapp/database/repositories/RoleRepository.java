package pl.exam.app.examapp.database.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.exam.app.examapp.database.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Integer>
{
	Role findByName(String name);
}
