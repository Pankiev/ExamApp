package pl.exam.app.business.exam.boundary;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import pl.exam.app.business.questionanswer.boundary.RestQuestionAnswerData;

import java.util.Date;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestUserExamData {
    private final RestExamData exam;
    private final Date assignmentDate;
    private final Date testApproachDate;
    private final List<RestQuestionAnswerData> questionsWithAnswers;
    private final Float totalScore;
    private boolean finished;
}
