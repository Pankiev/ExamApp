package pl.exam.app.jsf.beans.exam;

import pl.exam.app.database.entities.Exam;
import pl.exam.app.database.entities.jointables.UserExam;
import pl.exam.app.database.repositories.ExamRepository;
import pl.exam.app.database.repositories.UserExamRepository;
import pl.exam.app.database.repositories.UserRepository;

import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@ManagedBean
@ViewScoped
public class ExamResultView
{
	private final UserExamRepository userExamRepository;
	private final Collection<UserExam> userExams = new ArrayList<>();
	private Integer examId;

	@Inject
	public ExamResultView(UserExamRepository userExamRepository)
	{
		this.userExamRepository = userExamRepository;
	}

	public void setExamId(Integer examId)
	{
		if(examId != null)
			this.examId = examId;
	}

	public Collection<UserExam> getUsersAssignedToExamData()
	{
		if(userExams.isEmpty())
			userExams.addAll(userExamRepository.findByKey_Exam_Id(examId));
		return userExams;
	}
}
