package pl.exam.app.business.exam.control;

import pl.exam.app.persistence.answer.Answer;
import pl.exam.app.persistence.question.Question;
import pl.exam.app.persistence.userexam.UserExam;

public interface ActiveTestUserQuestionAction {
    void apply(UserExam userExam, Question question, Answer answer);
}
