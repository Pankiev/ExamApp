package pl.exam.app.database.entities.jointables;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.exam.app.database.entities.Exam;
import pl.exam.app.database.entities.User;
import pl.exam.app.database.entities.components.UserExamKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "CharactersQuests")
@Table(name = "characters_quests")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "key")
public class UserExam
{
	@EmbeddedId
	private UserExamKey key = new UserExamKey();

	public UserExam(User user, Exam exam)
	{
		setUser(user);
		setExam(exam);
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
