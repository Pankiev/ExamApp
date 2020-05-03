package pl.exam.app.business.registeration;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.exam.app.business.authentication.control.AuthenticationService;
import pl.exam.app.persistence.role.Role;
import pl.exam.app.persistence.role.RoleRepository;
import pl.exam.app.persistence.user.User;
import pl.exam.app.persistence.user.UserRepository;

import java.util.Collections;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    public RegisterService(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
    }

    public String registerStudent(RegisterRequest request) {
        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(encryptedPassword);
        newUser.setSchoolClass(request.getSchoolClass());
        newUser.setIdInClass(request.getIdInClass());
        Role studentRole = roleRepository.findByName("student");
        newUser.setRoles(Collections.singleton(studentRole));
        userRepository.save(newUser);
        return authenticationService.authenticate(request.getUsername(), request.getPassword());
    }
}
