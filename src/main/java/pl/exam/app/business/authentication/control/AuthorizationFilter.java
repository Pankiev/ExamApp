package pl.exam.app.business.authentication.control;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;


public class AuthorizationFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;

    public AuthorizationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        if (shouldOmitAuthorizationChecking(request)) {
            chain.doFilter(request, response);
            return;
        }
        String authorizationToken = Optional.ofNullable(request.getHeader("Authorization"))
                .orElseThrow(() -> new AuthenticationException("No authorization token provided"));
        authenticationService.validate(authorizationToken);
        chain.doFilter(request, response);
    }

    private boolean shouldOmitAuthorizationChecking(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return Objects.equals(request.getMethod(), RequestMethod.OPTIONS.name()) ||
                requestURI.endsWith("/login") || requestURI.endsWith("/register");
    }
}
