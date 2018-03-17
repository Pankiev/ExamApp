package pl.exam.app.jsf.beans.exam;

import pl.exam.app.database.entities.Exam;
import pl.exam.app.database.entities.jointables.UserExam;
import pl.exam.app.database.repositories.ExamRepository;
import pl.exam.app.database.repositories.UserExamRepository;
import pl.exam.app.database.repositories.UserRepository;

import javax.annotation.ManagedBean;
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
	private final UserExamRepository userExamRepository;
	private Integer examId;

	@Inject
	public ExamResultView(UserRepository userRepository, ExamRepository examRepository,
			UserExamRepository userExamRepository)
	{
		this.userRepository = userRepository;
		this.examRepository = examRepository;
		this.userExamRepository = userExamRepository;
	}

	public void setExamId(Integer examId)
	{
		if(examId != null)
			this.examId = examId;
	}

	public Collection<UserExam> getUsersAssignedToExamData()
	{
		return userExamRepository.findByKey_Exam_Id(examId);
	}
}
