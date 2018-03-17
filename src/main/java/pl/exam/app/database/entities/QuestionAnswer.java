package pl.exam.app.database.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity(name = "QuestionAnswer")
@Table(name = "exam_result_question_answers")
@Data
@EqualsAndHashCode(of = { "question", "answer" })
public class QuestionAnswer
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id", nullable = true)
	private Question question;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "answer_id", nullable = true)
	private Answer answer;
}
