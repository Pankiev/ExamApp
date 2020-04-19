package pl.exam.app.configuration.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import pl.exam.app.persistence.Authority;
import pl.exam.app.persistence.role.Role;
import pl.exam.app.persistence.user.User;
import pl.exam.app.persistence.user.UserRepository;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

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
        Date expirationDate = getExpirationDate();
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expirationDate)
                .withClaim("Authorities", authorities)
                .withIssuedAt(Date.from(Instant.now()))
                .sign(Algorithm.HMAC512(user.getPassword().getBytes()));
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
