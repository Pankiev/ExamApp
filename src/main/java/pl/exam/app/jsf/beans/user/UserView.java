package pl.exam.app.jsf.beans.user;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.springframework.dao.DataIntegrityViolationException;
import pl.exam.app.database.entities.User;
import pl.exam.app.database.repositories.RoleRepository;
import pl.exam.app.database.repositories.UserRepository;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.util.Collections;

@ManagedBean
@ViewScoped
public class UserView
{
	@Getter
	private final LazyUserDataModel lazyModel;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	@Getter
	@Setter
	private User selectedUser;

	@Inject
	UserView(UserRepository userRepository, RoleRepository roleRepository)
	{
		lazyModel = new LazyUserDataModel(userRepository);
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public Iterable<String> getAllClasses()
	{
		return userRepository.findDistinctSchoolClasses();
	}

	public void saveSelectedUser()
	{
		try
		{
			if(selectedUser.getIdInClass() != null && selectedUser.getSchoolClass() != null)
				selectedUser.setRoles(Sets.newHashSet(roleRepository.findByName("student")));

			userRepository.save(selectedUser);
			showSavedMessage();
		} catch (DataIntegrityViolationException e)
		{
			showErrorMessage();
		}
	}

	private void showSavedMessage()
	{
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "User saved", null));
	}

	private void showErrorMessage()
	{
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "There is already a user with this class id", null));
	}
}
