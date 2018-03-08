package pl.exam.app.examapp.jsf.beans.exam.event;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import pl.exam.app.examapp.database.entities.ExamEvent;
import pl.exam.app.examapp.database.repositories.ExamEventRepository;

@ManagedBean
@Component
@ViewScoped
public class ExamEventViewer
{
	@Inject
	private ExamEventRepository examEventRepository;
	
	public Iterable<ExamEvent> getOpenedExamEvents()
	{
		return examEventRepository.findByOpenedTrueOrderByCreationDateDesc();
	}

	public Iterable<ExamEvent> getClosedExamEvents()
	{
		return examEventRepository.findByOpenedFalseOrderByCreationDateDesc();
	}
	
	public Iterable<ExamEvent> getAllExamEvents()
	{
		return examEventRepository.findAllByOrderByCreationDateDesc();
	}
}
