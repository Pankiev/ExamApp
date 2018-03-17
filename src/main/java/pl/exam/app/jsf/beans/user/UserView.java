package pl.exam.app.jsf.beans.user;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;
import org.springframework.dao.DataIntegrityViolationException;
import pl.exam.app.database.entities.User;
import pl.exam.app.database.repositories.UserRepository;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@ManagedBean
@ViewScoped
public class UserView
{
	@Getter
	private final LazyUserDataModel lazyModel;
	@Getter
	private final UserRepository userRepository;
	@Getter
	@Setter
	private User selectedUser;
	private Object getClasses;

	@Inject
	UserView(UserRepository userRepository)
	{
		lazyModel = new LazyUserDataModel(userRepository);
		this.userRepository = userRepository;
	}

	public Iterable<String> getAllClasses()
	{
		Iterable<String> distinctSchoolClasses = userRepository.findDistinctSchoolClasses();
		return distinctSchoolClasses;
	}

	public void saveSelectedUser()
	{
		try
		{
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
