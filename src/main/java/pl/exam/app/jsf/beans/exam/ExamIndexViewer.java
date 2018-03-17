package pl.exam.app.jsf.beans.exam;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.omnifaces.util.Faces;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import pl.exam.app.database.entities.Exam;
import pl.exam.app.database.repositories.ExamRepository;
import pl.exam.app.database.repositories.UserRepository;

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

	public Collection<Exam> getLoggedUserExams()
	{
		String username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		return examRepository.findByUsers_Key_User_Nickname(username);
	}
}
