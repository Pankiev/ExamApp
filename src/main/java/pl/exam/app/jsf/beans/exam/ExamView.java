package pl.exam.app.jsf.beans.exam;

import lombok.Getter;
import lombok.Setter;
import pl.exam.app.database.entities.Exam;
import pl.exam.app.database.entities.User;
import pl.exam.app.database.entities.jointables.UserExam;
import pl.exam.app.database.repositories.ExamRepository;
import pl.exam.app.database.repositories.UserExamRepository;
import pl.exam.app.database.repositories.UserRepository;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ManagedBean
@ViewScoped
public class ExamView
{
	private final UserRepository userRepository;
	private final ExamRepository examRepository;
	private final UserExamRepository userExamRepository;
	private Integer examId;
	private Collection<User> selectedUsers = Collections.emptyList();
	@Getter
	@Setter
	private String selectedClass;

	@Inject
	public ExamView(UserRepository userRepository, ExamRepository examRepository, UserExamRepository userExamRepository)
	{
		this.userRepository = userRepository;
		this.examRepository = examRepository;
		this.userExamRepository = userExamRepository;
	}

	public Iterable<String> getSchoolClasses()
	{
		return userRepository.findDistinctSchoolClasses();
	}

	public void onClassChange()
	{
		selectedUsers = userRepository.findBySchoolClass(selectedClass);
	}

	public Collection<User> getSelectedUsers()
	{
		return selectedUsers;
	}

	public void setExamId(Integer examId)
	{
		if(examId != null)
			this.examId = examId;
	}

	public void assignUsersToExam()
	{
		Set<UserExam> newRecords = getNewRecords();
		userExamRepository.saveAll(newRecords);
		showSavedMessage();
	}

	private Set<UserExam> getNewRecords()
	{
		Exam exam = examRepository.findById(examId).get();
		Set<UserExam> userExams = exam.getUsers();
		Set<User> currentUsers = userExams.stream().map(UserExam::getUser)
				.collect(Collectors.toSet());
		Set<UserExam> newRecords = new HashSet<>();
		for (User user : selectedUsers)
			if(!currentUsers.contains(user))
				newRecords.add(new UserExam(user, exam));
		return newRecords;
	}

	private void showSavedMessage()
	{
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Users saved"));
	}
}
