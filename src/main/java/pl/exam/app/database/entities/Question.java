package pl.exam.app.database.entities;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "Question")
@Table(name = "questions")
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude="exam")
public class Question
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "question", nullable = false)
	private String question;
	
	@Column(name = "seconds_for_answer", nullable=false)
	private Integer secondsForAnswer;
	
	@OneToMany(mappedBy="question", cascade=CascadeType.ALL)
	private List<Answer> answers;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "exam_id")
	private Exam exam;
}
