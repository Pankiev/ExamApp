package pl.exam.app.jsf.beans.exam;

import pl.exam.app.database.entities.Exam;
import pl.exam.app.database.entities.User;
import pl.exam.app.database.repositories.ExamRepository;
import pl.exam.app.database.repositories.UserRepository;

import javax.annotation.ManagedBean;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

@ManagedBean
@ViewScoped
public class ExamResultView
{
	private final UserRepository userRepository;
	private final ExamRepository examRepository;
	private Integer examId;

	@Inject
	public ExamResultView(UserRepository userRepository, ExamRepository examRepository)
	{
		this.userRepository = userRepository;
		this.examRepository = examRepository;
	}

	public void setExamId(Integer examId)
	{
		if(examId != null)
			this.examId = examId;
	}

	public Collection<User> getUsersAssignedToExam()
	{
		Optional<Exam> exam = examRepository.findById(examId);
		return userRepository.findByExams(exam.get());
	}
}
