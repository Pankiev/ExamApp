package pl.exam.app.business.authentication.control;

public class TokenValidationException extends AuthenticationException {

    public TokenValidationException(String message) {
        super(message);
    }

    public TokenValidationException(String message, Exception e) {
        super(message, e);
    }
}
