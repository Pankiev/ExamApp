package pl.exam.app.business.authentication.control;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.exam.app.persistence.Authority;
import pl.exam.app.persistence.role.Role;
import pl.exam.app.persistence.user.User;
import pl.exam.app.persistence.user.UserRepository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException("Invalid credentials"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Invalid credentials");
        }
        List<String> authorities = getGrantedAuthorities(user);
        Date expirationDate = getExpirationDate();
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expirationDate)
                .withClaim("Authorities", authorities)
                .withIssuedAt(Date.from(Instant.now()))
                .sign(getAlgorithm(user.getPassword()));
    }

    private Algorithm getAlgorithm(String password) {
        return Algorithm.HMAC512(password.getBytes());
    }

    private List<String> getGrantedAuthorities(User user) {
        return user.getRoles().stream()
                .map(Role::getAuthorities)
                .flatMap(Collection::stream)
                .map(Authority::getName)
                .collect(Collectors.toList());
    }

    private Date getExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        return calendar.getTime();
    }

    public void validate(String token) {
        try {
            doValidate(token);
        } catch (Exception e) {
            throw new AuthenticationException("Invalid token", e);
        }
    }

    private void doValidate(String token) {
        DecodedJWT decoded = JWT.decode(token);
        if (hasTokenExpired(decoded)) {
            throw new TokenValidationException();
        }
        String username = decoded.getSubject();
        String encryptedPassword = userRepository.findByUsername(username)
                .map(User::getPassword)
                .orElseThrow(TokenValidationException::new);
        JWT.require(Algorithm.HMAC512(encryptedPassword))
                .build()
                .verify(decoded);
    }

    private boolean hasTokenExpired(DecodedJWT jwt) {
        return Date.from(Instant.now()).after(jwt.getExpiresAt());
    }
}
