package pl.exam.app.examapp.jsf.beans.exam.event;


import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import pl.exam.app.examapp.database.entities.ExamEvent;
import pl.exam.app.examapp.database.repositories.ExamEventRepository;

@ManagedBean
@Component
@ViewScoped
public class ExamEventStarter
{
	@Inject
	private ExamEventRepository examEventRepository;

	private Integer examEventId;
	
	public void open()
	{
		ExamEvent examEvent = examEventRepository.findById(examEventId).get();
		examEvent.setOpened(true);
		examEventRepository.save(examEvent);
	} 

	public Integer getExamEventId()
	{
		return examEventId;
	}

	public void setExamEventId(Integer examEventId)
	{
		this.examEventId = examEventId;
	}
}