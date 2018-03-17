package pl.exam.app.jsf.beans.exam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import pl.exam.app.database.entities.Answer;
import pl.exam.app.database.entities.Exam;
import pl.exam.app.database.entities.Question;
import pl.exam.app.database.repositories.ExamRepository;

@ManagedBean
@ViewScoped
@Component
public class ExamCreator
{
	@Inject
	private ExamRepository examRepository;
	
	@Getter
	@Setter
	private String examName = "Insert exam name.";
	
	private Collection<Question> questions = new ArrayList<>(Collections.singleton(createDefaultQuestion()));

	private Question createDefaultQuestion()
	{
		Question question = new Question();
		question.setQuestion("Insert question");
		question.setSecondsForAnswer(30);
		Answer defaultAnswer = createDefaultAnswerForQuestion(question);
		defaultAnswer.setValid(true);
		question.setAnswers(new ArrayList<>(Collections.singleton(defaultAnswer)));
		return question;
	}
	
	public void addQuestion()
	{
		questions.add(createDefaultQuestion());
	}
	
	public Collection<Question> getQuestions()
	{
		return questions;
	}
	
	public void removeQuestion(Question question)
	{
		questions.removeIf(q -> q == question);
	}
	
	public void save()
	{
		try
		{
			Exam exam = new Exam();
			exam.setName(examName);
			exam.setQuestions(questions);
			questions.forEach(q -> q.setExam(exam));
			examRepository.save(exam);
			showSaveMessage();
			redirect(exam.getId());
		} catch (Exception e)
		{
			showErrorMessage("Exam could not be saved!");
		}

	}

	private void redirect(Integer id)
	{
		String url = "/exam/" + id;
		try
		{
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (IOException e)
		{
			showErrorMessage(e.getMessage());
		}
	}

	private void showSaveMessage()
	{
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Exam definition saved"));
	}
	
	private void showErrorMessage(String message)
	{
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", message));
	}
 
	public void addQuestionAnswer(Question question)
	{
		question.getAnswers().add(createDefaultAnswerForQuestion(question));
	}
	
	private Answer createDefaultAnswerForQuestion(Question question)
	{
		Answer answer = new Answer();
		answer.setQuestion(question);
		answer.setAnswer("Insert answer");
		return answer;
	}
	
	public void selectAnswer(Question question, Answer answer)
	{
		question.getAnswers().forEach(a -> a.setValid(false));
		answer.setValid(true);
	}
	
	public void removeAnswer(Question question, Answer answer)
	{
		question.getAnswers().removeIf(a -> a == answer);
	}
}