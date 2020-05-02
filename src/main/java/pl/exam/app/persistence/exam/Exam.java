package pl.exam.app.persistence.exam;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import pl.exam.app.persistence.question.Question;
import pl.exam.app.persistence.userexam.UserExam;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Table(name = "exams")
@Entity(name = "Exam")
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"questions", "users"})
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private Set<Question> questions;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @OneToMany(mappedBy = "key.exam")
    private Set<UserExam> users;
}
