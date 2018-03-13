package pl.exam.app.examapp.jsf.beans.exam;

import java.util.Collection;

import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import pl.exam.app.examapp.database.entities.Exam;
import pl.exam.app.examapp.database.repositories.ExamRepository;

@ManagedBean
@Component
@ViewScoped
public class ExamViewer
{
	@Inject
	private ExamRepository examRepository;
	
	public Collection<Exam> getExams()
	{
		return Lists.newArrayList(examRepository.findAll());
	}
}
