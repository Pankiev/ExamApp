package pl.exam.app.jsf.beans.exam;

import pl.exam.app.database.entities.jointables.UserExam;
import pl.exam.app.database.repositories.UserExamRepository;

import javax.annotation.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

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

	public UserExam getUserExamResult()
	{
		return userExamRepository.findByKeyExamIdAndKeyUserNickname(examId, getUsername());
	}

	private String getUsername()
	{
		return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
	}

	public Collection<UserExam> getUsersAssignedToExamData()
	{
		if(userExams.isEmpty())
			userExams.addAll(userExamRepository.findByKey_Exam_Id(examId));
		return userExams;
	}
}
