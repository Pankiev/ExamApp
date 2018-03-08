package pl.exam.app.examapp.database.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	
	@OneToMany(mappedBy="exam")
	private Collection<ExamEvent> examEvents;
	
	@OneToMany(mappedBy="exam", cascade=CascadeType.ALL)
	private Collection<Question> questions;
}
