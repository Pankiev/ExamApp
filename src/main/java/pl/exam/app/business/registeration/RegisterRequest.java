package pl.exam.app.business.registeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterRequest {
    private final String username;
    private final String password;
    private final String schoolClass;
    private final Integer idInClass;

    @JsonCreator
    public RegisterRequest(@JsonProperty("username") String username,
                           @JsonProperty("password") String password,
                           @JsonProperty("schoolClass") String schoolClass,
                           @JsonProperty("idInClass") Integer idInClass) {
        this.username = username;
        this.password = password;
        this.schoolClass = schoolClass;
        this.idInClass = idInClass;
    }
}
