package pl.exam.app.database.entities;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.exam.app.database.entities.jointables.UserExam;

@Table(name = "exams")
@Entity(name = "Exam")
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"questinos", "users"})
public class Exam
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
	private List<Question> questions;

	@OneToMany(mappedBy = "key.exam")
	private Set<UserExam> users;
}
