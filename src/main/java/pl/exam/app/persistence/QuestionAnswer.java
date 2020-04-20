package pl.exam.app.persistence;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.exam.app.persistence.question.Question;
import pl.exam.app.persistence.userexam.UserExam;

import javax.persistence.*;

@Entity(name = "QuestionAnswer")
@Table(name = "exam_result_question_answers")
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class QuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    public QuestionAnswer(UserExam userExam, Question question, Answer answer) {
        this.userExam = userExam;
        this.question = question;
        this.answer = answer;
    }
}
