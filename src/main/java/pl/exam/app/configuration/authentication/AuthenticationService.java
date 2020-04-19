package pl.exam.app.configuration.authentication;

import org.springframework.stereotype.Service;
import pl.exam.app.persistence.Authority;
import pl.exam.app.persistence.role.Role;
import pl.exam.app.persistence.user.User;
import pl.exam.app.persistence.user.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JWTGenerator jwtGenerator = new JWTGenerator();

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(AuthenticationException::new);
        if (!Objects.equals(user.getPassword(), password)) {
            throw new AuthenticationException();
        }
        List<String> authorities = getGrantedAuthorities(user);
        return jwtGenerator.generate(username, authorities);
    }

    private List<String> getGrantedAuthorities(User user) {
        return user.getRoles().stream()
                .map(Role::getAuthorities)
                .flatMap(Collection::stream)
                .map(Authority::getName)
                .collect(Collectors.toList());
    }
}
