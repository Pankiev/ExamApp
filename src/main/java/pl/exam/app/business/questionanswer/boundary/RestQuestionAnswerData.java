package pl.exam.app.business.questionanswer.boundary;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import pl.exam.app.business.exam.boundary.RestAnswerData;
import pl.exam.app.business.exam.boundary.RestQuestionData;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestQuestionAnswerData {
    private final RestQuestionData question;
    private final RestAnswerData answer;
}
