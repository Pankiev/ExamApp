package pl.exam.app.business.exam.boundary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestAnswerData {
    private final Long id;
    private final boolean valid;
    private final String answer;

    @JsonCreator
    private RestAnswerData(@JsonProperty("id") Long id,
                           @JsonProperty("valid") boolean valid,
                           @JsonProperty("answer") String answer) {
        this.id = id;
        this.valid = valid;
        this.answer = answer;
    }
}
