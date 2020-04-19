package pl.exam.app.business.exam.boundary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;

@Data
public class CreateExamRequest {
    private final String name;
    private final Collection<RequestQuestion> questions;

    @JsonCreator
    public CreateExamRequest(@JsonProperty("name") String name,
                             @JsonProperty("questions") Collection<RequestQuestion> questions) {
        this.name = name;
        this.questions = questions;
    }
}
