package pl.exam.app.persistence.questionanswer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.exam.app.persistence.answer.Answer;
import pl.exam.app.persistence.question.Question;
import pl.exam.app.persistence.questionanswer.QuestionAnswerKey;
import pl.exam.app.persistence.userexam.UserExam;
import pl.exam.app.persistence.userexam.UserExamKey;

import javax.persistence.*;

@Entity(name = "QuestionAnswer")
@Table(name = "exam_result_question_answers")
@Data
@NoArgsConstructor
public class QuestionAnswer {
    @EmbeddedId
    private QuestionAnswerKey key = new QuestionAnswerKey();

    public QuestionAnswer(UserExam userExam, Question question, Answer answer) {
        this.key.setUserExam(userExam);
        this.key.setQuestion(question);
        this.key.setAnswer(answer);
    }

    public UserExam getUserExam() {
        return this.key.getUserExam();
    }

    public Question getQuestion() {
        return this.key.getQuestion();
    }

    public Answer getAnswer() {
        return this.key.getAnswer();
    }

    public void setAnswer(Answer answer) {
        this.key.setAnswer(answer);
    }
}
