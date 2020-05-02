package pl.exam.app.business.exam.boundary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import pl.exam.app.persistence.userexam.UserExam;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestExamData {
    private final Long id;
    private final String name;
    private final Collection<RestQuestionData> questions;

    @JsonCreator
    private RestExamData(@JsonProperty("id") Long id,
                        @JsonProperty("name") String name,
                        @JsonProperty("questions") Collection<RestQuestionData> questions) {
        this.id = id;
        this.name = name;
        this.questions = Objects.requireNonNullElse(questions, Collections.emptyList());
    }
}
