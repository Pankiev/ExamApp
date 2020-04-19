package pl.exam.app.persistence;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.exam.app.persistence.question.Question;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Answer")
@Table(name = "answers")
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = "question")
public class Answer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "valid", nullable = false)
    private Boolean valid = false;

    @Column(name = "answer", nullable = false)
    private String answer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id")
    private Question question;
}
