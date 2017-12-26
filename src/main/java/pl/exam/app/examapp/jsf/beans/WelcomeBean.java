package pl.exam.app.examapp.jsf.beans;

import javax.annotation.ManagedBean;

@ManagedBean("editor")
public class WelcomeBean
{
	private String value = "This editor is provided by PrimeFaces";

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
