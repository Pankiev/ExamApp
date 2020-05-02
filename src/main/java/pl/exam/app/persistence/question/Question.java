package pl.exam.app.persistence.question;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.exam.app.persistence.answer.Answer;
import pl.exam.app.persistence.exam.Exam;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Question")
@Table(name = "questions")
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = "exam")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "seconds_for_answer", nullable = false)
    private Integer secondsForAnswer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exam_id")
    private Exam exam;
}
