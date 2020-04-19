package pl.exam.app.jsf.beans.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.dao.DataIntegrityViolationException;
import pl.exam.app.database.entities.User;
import pl.exam.app.database.repositories.UserRepository;
import pl.exam.app.jsf.beans.helpers.Dictionary;
import pl.exam.app.persistence.role.RoleRepository;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.util.Collections;

@ManagedBean
@ViewScoped
public class UserView {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Dictionary dictionary;
    @Getter
    private final LazyUserDataModel lazyModel;
    @Getter
    @Setter
    private User selectedUser;

    @Inject
    UserView(UserRepository userRepository, RoleRepository roleRepository, Dictionary dictionary) {
        lazyModel = new LazyUserDataModel(userRepository);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.dictionary = dictionary;
    }

    public Iterable<String> getAllClasses() {
        return userRepository.findDistinctSchoolClasses();
    }

    public void saveSelectedUser() {
        try {
            if (selectedUser.getIdInClass() != null && selectedUser.getSchoolClass() != null)
                selectedUser.setRoles(Collections.singleton(roleRepository.findByName("student")));

            userRepository.save(selectedUser);
            showSavedMessage();
        } catch (DataIntegrityViolationException e) {
            showErrorMessage();
        }
    }

    private void showSavedMessage() {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, dictionary.getMessage("User.saved"), null));
    }

    private void showErrorMessage() {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        dictionary.getMessage("There.is.already.a.user.with.this.class.id"), null));
    }

    public void deleteSelectedUser() {
        userRepository.delete(selectedUser);
    }
}
