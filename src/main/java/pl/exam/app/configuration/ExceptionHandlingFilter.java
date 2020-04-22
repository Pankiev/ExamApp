package pl.exam.app.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.exam.app.business.authentication.control.AuthenticationException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlingFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(ExceptionHandlingFilter.class);
    private final String allowedOrigin;

    public ExceptionHandlingFilter(String allowedOrigin) {
        this.allowedOrigin = allowedOrigin;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            handleError(response, HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            logger.error("Error on request filter", e);
            handleError(response, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    void handleError(HttpServletResponse response, HttpStatus status, String errorMessage) throws IOException {
        response.setStatus(status.value());
        response.setHeader("Content-Type", MediaType.TEXT_PLAIN_VALUE);
        response.setHeader("Access-Control-Allow-Origin", allowedOrigin);
        response.getWriter().write(errorMessage);
    }
}
