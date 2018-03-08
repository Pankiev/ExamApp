package pl.exam.app.examapp.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "Answer")
@Table(name = "answers")
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude="question")
public class Answer
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "valid", nullable = false)
	private Boolean valid = false;
	
	@Column(name = "answer", nullable = false)
	private String answer;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="question_id")
	private Question question;
}
