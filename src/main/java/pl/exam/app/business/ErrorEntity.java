package pl.exam.app.business;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class ErrorEntity extends ResponseEntity<String> {

    public static ErrorEntity notFound(String errorMessage) {
        return new ErrorEntity(errorMessage, HttpStatus.NOT_FOUND);
    }

    public static ErrorEntity forbidden(String errorMessage) {
        return new ErrorEntity(errorMessage, HttpStatus.FORBIDDEN);
    }

    private ErrorEntity(String errorMessage, HttpStatus status) {
        super(errorMessage, errorHeaders(), status);
    }

    private static MultiValueMap<String, String> errorHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
        return httpHeaders;
    }

}
