package pl.exam.app.business.authentication.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.header.Header;
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
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest request) {
        String authToken = authenticationService.authenticate(request.getUsername(), request.getPassword());
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(authToken);
    }

    @ExceptionHandler
    private ResponseEntity<String> handle(AuthenticationException e) {
        String errorMessage = e.getClass().getSimpleName() + ": Invalid username or password";
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.TEXT_PLAIN)
                .body(errorMessage);
    }
}
