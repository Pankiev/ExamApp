package pl.exam.app.business.authentication.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.exam.app.business.authentication.control.AuthenticationException;
import pl.exam.app.business.authentication.control.AuthenticationService;

@RestController
public class LoginController {

    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthenticationRequest request) {
        return authenticationService.authenticate(request.getUsername(), request.getPassword());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    private String handle(AuthenticationException e) {
        return "Invalid username or password";
    }
}
