package pl.exam.app.jsf.beans.helpers;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ResourceBundle;

@Component("DictionaryHelper")
@SessionScoped
public class Dictionary implements Serializable
{
	private transient ResourceBundle resourceBundle;

	@PostConstruct
	public void initialize()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		resourceBundle = context.getApplication().getResourceBundle(context, "dictionary");
	}

	public String getMessage(String key)
	{
		return resourceBundle.getString(key);
	}

}