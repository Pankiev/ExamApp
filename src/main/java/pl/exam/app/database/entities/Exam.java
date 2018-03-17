package pl.exam.app.database.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "exams")
@Entity(name = "Exam")
@Data
@EqualsAndHashCode(of = "id")
public class Exam
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
	private Collection<Question> questions;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_exam",
			joinColumns = @JoinColumn(name = "exam_id", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false)
	)
	private Set<User> users = new HashSet<>();
}
