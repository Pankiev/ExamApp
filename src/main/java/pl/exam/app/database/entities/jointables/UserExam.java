package pl.exam.app.database.entities.jointables;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.exam.app.database.entities.Answer;
import pl.exam.app.database.entities.Exam;
import pl.exam.app.database.entities.QuestionAnswer;
import pl.exam.app.database.entities.User;
import pl.exam.app.database.entities.components.UserExamKey;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "UserExam")
@Table(name = "user_exam")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "key")
@ToString(exclude = {"questionsWithAnswers", "key"})
public class UserExam
{
	@EmbeddedId
	private UserExamKey key = new UserExamKey();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "assignment_date")
	private Date assignmentDate = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "test_approach_date")
	private Date testApproachDate;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userExam")
	private Set<QuestionAnswer> questionsWithAnswers;

	@Column(name = "totalScore")
	private Float totalScore;

	@Column(name = "finished")
	private Boolean finished = false;

	public UserExam(User user, Exam exam)
	{
		setUser(user);
		setExam(exam);
	}

	public UserExam(UserExamKey userExamKey)
	{
		setKey(userExamKey);
	}

	public User getUser()
	{
		return key.getUser();
	}

	public void setUser(User user)
	{
		key.setUser(user);
	}

	public Exam getExam()
	{
		return key.getExam();
	}

	public void setExam(Exam exam)
	{
		key.setExam(exam);
	}
}
