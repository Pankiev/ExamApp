package pl.exam.app.business.user.boundary;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestUserData {
    private final Long id;
    private final String username;
    private final String schoolClass;
    private final int idInClass;
    private final Date creationDate;
}
