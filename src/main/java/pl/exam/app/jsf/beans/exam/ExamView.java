package pl.exam.app.jsf.beans.exam;

import lombok.Getter;
import lombok.Setter;
import pl.exam.app.database.entities.Exam;
import pl.exam.app.database.entities.User;
import pl.exam.app.database.repositories.ExamRepository;
import pl.exam.app.database.repositories.UserRepository;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@ManagedBean
@ViewScoped
public class ExamView
{
	private final UserRepository userRepository;
	private final ExamRepository examRepository;
	private Integer examId;
	private Collection<User> users = Collections.emptyList();
	@Getter
	@Setter
	private String selectedClass;

	@Inject
	public ExamView(UserRepository userRepository, ExamRepository examRepository)
	{
		this.userRepository = userRepository;
		this.examRepository = examRepository;
	}

	public Iterable<String> getSchoolClasses()
	{
		return userRepository.findDistinctSchoolClasses();
	}

	public void onClassChange()
	{
		users = userRepository.findBySchoolClass(selectedClass);
	}

	public Collection<User> getUsers()
	{
		return users;
	}

	public void setExamId(Integer examId)
	{
		if(examId != null)
			this.examId = examId;
	}

	public void assignUsersToExam()
	{
		Exam exam = examRepository.findById(examId).get();
		exam.getUsers().addAll(users);
		examRepository.save(exam);
		showSavedMessage();
	}

	private void showSavedMessage()
	{
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Users saved"));
	}
}
