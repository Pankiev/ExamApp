package pl.exam.app.configuration.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.SneakyThrows;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;


public class AuthorizationFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;

    public AuthorizationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        if (request.getRequestURI().contains("/login")) {
            chain.doFilter(request, response);
            return;
        }
        String authorizationToken = Optional.ofNullable(request.getHeader("Authorization"))
                .orElseThrow(() -> new RuntimeException("No authorization token provided"));
        authenticationService.validate(authorizationToken);
        chain.doFilter(request, response);
    }


    private boolean hasTokenExpired(DecodedJWT jwt) {
        return Date.from(Instant.now()).after(jwt.getExpiresAt());
    }
}
