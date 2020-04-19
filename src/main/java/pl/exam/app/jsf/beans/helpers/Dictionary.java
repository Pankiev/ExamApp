package pl.exam.app.jsf.beans.helpers;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ResourceBundle;

@Component("DictionaryHelper")
public class Dictionary implements Serializable {
    private transient ResourceBundle resourceBundle;

    @PostConstruct
    private void initialize() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        resourceBundle = context.getApplication().getResourceBundle(context, "dictionary");
    }

    public String getMessage(String key) {
        return key;
        //return resourceBundle.getString(key);
    }

}
