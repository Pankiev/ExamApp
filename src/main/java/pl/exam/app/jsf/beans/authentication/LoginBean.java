package pl.exam.app.jsf.beans.authentication;

import pl.exam.app.jsf.beans.helpers.Dictionary;

import javax.annotation.ManagedBean;
import java.io.Serializable;
import java.util.Map;

@ManagedBean
public class LoginBean implements Serializable {
    private final transient Dictionary dictionary;

    public LoginBean(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void initErrorMessage() {
        if (errorOccured())
            showErrorMessage();
    }

    private boolean errorOccured() {
//        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext()
//                .getRequestParameterMap();
//        return requestParameterMap.get("error") != null;
        return false;
    }

    private void showErrorMessage() {
//        FacesContext.getCurrentInstance().addMessage(null,
//                new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                        dictionary.getMessage("Username.or.password.is.invalid"),
//                        dictionary.getMessage("or.user.is.already.logged.in")));
    }
}
