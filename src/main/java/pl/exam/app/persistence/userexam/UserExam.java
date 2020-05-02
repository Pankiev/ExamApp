package pl.exam.app.persistence.userexam;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.exam.app.persistence.questionanswer.QuestionAnswer;
import pl.exam.app.persistence.user.User;
import pl.exam.app.persistence.exam.Exam;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "UserExam")
@Table(name = "user_exam")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "key")
@ToString(exclude = {"questionsWithAnswers", "key"})
public class UserExam {
    @EmbeddedId
    private UserExamKey key = new UserExamKey();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "assignment_date")
    private Date assignmentDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "test_approach_date")
    private Date testApproachDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "key.userExam")
    private Set<QuestionAnswer> questionsWithAnswers = new HashSet<>();

    @Column(name = "totalScore")
    private Float totalScore;

    @Column(name = "finished")
    private boolean finished = false;

    public UserExam(User user, Exam exam) {
        setUser(user);
        setExam(exam);
    }

    public UserExam(UserExamKey userExamKey) {
        setKey(userExamKey);
    }

    public User getUser() {
        return key.getUser();
    }

    public void setUser(User user) {
        key.setUser(user);
    }

    public Exam getExam() {
        return key.getExam();
    }

    public void setExam(Exam exam) {
        key.setExam(exam);
    }
}
