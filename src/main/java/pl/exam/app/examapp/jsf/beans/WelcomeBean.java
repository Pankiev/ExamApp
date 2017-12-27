package pl.exam.app.examapp.jsf.beans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="editor")
@ApplicationScoped
public class WelcomeBean
{
	private String value = "This editor is provided by PrimeFaces";

	public String getValue() {
		return "asdasdasd " + value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
