package pl.exam.app.examapp.jsf.beans.authentication;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;


@ApplicationScoped
@ManagedBean
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
