package pl.exam.app.jsf.beans.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.dao.DataIntegrityViolationException;
import pl.exam.app.persistence.user.User;
import pl.exam.app.persistence.user.UserRepository;
import pl.exam.app.persistence.role.RoleRepository;

import javax.annotation.ManagedBean;
import java.util.Collections;

@ManagedBean
public class UserView {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Getter
    @Setter
    private User selectedUser;

    UserView(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
//        FacesContext.getCurrentInstance().addMessage(null,
//                new FacesMessage(FacesMessage.SEVERITY_INFO, dictionary.getMessage("User.saved"), null));
    }

    private void showErrorMessage() {
//        FacesContext.getCurrentInstance().addMessage(null,
//                new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                        dictionary.getMessage("There.is.already.a.user.with.this.class.id"), null));
    }

    public void deleteSelectedUser() {
        userRepository.delete(selectedUser);
    }
}
