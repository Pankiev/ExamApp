package pl.exam.app.persistence.questionanswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.exam.app.persistence.answer.Answer;
import pl.exam.app.persistence.question.Question;
import pl.exam.app.persistence.userexam.UserExam;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswerKey implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false),
            @JoinColumn(name = "exam_id", referencedColumnName = "exam_id", nullable = false)})
    private UserExam userExam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = true)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = true)
    private Answer answer;
}
