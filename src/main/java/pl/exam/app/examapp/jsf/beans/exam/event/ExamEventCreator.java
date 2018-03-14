package pl.exam.app.examapp.jsf.beans.exam.event;

import java.util.*;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;
import pl.exam.app.examapp.database.entities.Exam;
import pl.exam.app.examapp.database.entities.ExamEvent;
import pl.exam.app.examapp.database.repositories.ExamEventRepository;
import pl.exam.app.examapp.database.repositories.ExamRepository;

@ManagedBean
@Component
@ViewScoped
public class ExamEventCreator
{
	@Inject
	private ExamRepository examRepository;
	
	@Inject
	private ExamEventRepository examEventRepository;
	
	@Getter
	private final ExamEvent examEvent = new ExamEvent();
	
	@Getter
	@Setter
	private Integer chosenExamId;
	
	public Collection<Exam> getExams()
	{ 
		ArrayList<Exam> exams = Lists.newArrayList(examRepository.findAll());
		exams.sort(Comparator.comparing(Exam::getName));
		return exams;
	}
	
	public void save()
	{
		try
		{
			Optional<Exam> exam = examRepository.findById(chosenExamId);
			examEvent.setExam(exam.orElse(null));
			if(!exam.isPresent())
			{
				showErrorMessage("You must choose exam!");
				return;
			}
			
			examEventRepository.save(examEvent);
			showSaveMessage("Exam event saved");
		}
		catch(Exception e)
		{
			showErrorMessage("Exam event could not be saved!");
		}
	}
	

	private void showSaveMessage(String message)
	{
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
	}
	
	private void showErrorMessage(String message)
	{
 		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", message));
	}
}
