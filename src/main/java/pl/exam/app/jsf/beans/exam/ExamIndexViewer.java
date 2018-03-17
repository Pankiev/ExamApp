package pl.exam.app.jsf.beans.exam;

import java.util.Collection;

import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import pl.exam.app.database.entities.Exam;
import pl.exam.app.database.repositories.ExamRepository;

@ManagedBean
@Component
@ViewScoped
public class ExamIndexViewer
{
	private final ExamRepository examRepository;

	@Inject
	public ExamIndexViewer(ExamRepository examRepository)
	{
		this.examRepository = examRepository;
	}

	public Collection<Exam> getExams()
	{
		return Lists.newArrayList(examRepository.findAll());
	}
}
