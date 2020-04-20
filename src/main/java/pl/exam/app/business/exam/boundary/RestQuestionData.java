package pl.exam.app.business.exam.boundary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestQuestionData {
    private final Long id;
    private final String question;
    private final Integer secondsForAnswer;
    private final Collection<RestAnswerData> answers;

    @JsonCreator
    private RestQuestionData(@JsonProperty("id") Long id,
                             @JsonProperty("question") String question,
                             @JsonProperty("secondsForAnswer") Integer secondsForAnswer,
                             @JsonProperty("answers") Collection<RestAnswerData> answers) {
        this.id = id;
        this.question = question;
        this.secondsForAnswer = secondsForAnswer;
        this.answers = Objects.requireNonNullElse(answers, Collections.emptyList());
    }
}
