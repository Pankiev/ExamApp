package pl.exam.app.database.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "User")
@Table(name = "users", uniqueConstraints =
@UniqueConstraint(columnNames = { "schoolClass", "idInClass" }))
@Data
@EqualsAndHashCode(of = "id")
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nickname", unique = true, nullable = false)
	private String nickname;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "schoolClass")
	private String schoolClass;

	@Column(name = "idInClass")
	private Integer idInClass;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable = false)
	private Date creationDate = new Date();

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Role> roles = new HashSet<>();

	@ManyToMany(fetch=FetchType.LAZY, mappedBy = "users")
	private Set<Exam> exams = new HashSet<>();
}
