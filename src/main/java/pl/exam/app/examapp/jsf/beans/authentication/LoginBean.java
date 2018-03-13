package pl.exam.app.examapp.jsf.beans.authentication;

import java.util.Map;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ManagedBean
@ApplicationScoped
public class LoginBean
{
	public void initErrorMessage()
	{
		if (errorOccured())
			showErrorMessage();
	}

	private boolean errorOccured()
	{
		Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
		boolean errorOccured = requestParameterMap.get("error") != null;
		return errorOccured;
	}

	private void showErrorMessage()
	{
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username or password is invalid!", "(or user is already logged in)"));
	}
}
