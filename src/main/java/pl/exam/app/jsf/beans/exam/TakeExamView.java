package pl.exam.app.jsf.beans.exam;

import pl.exam.app.database.entities.Answer;
import pl.exam.app.database.entities.Question;
import pl.exam.app.database.entities.QuestionAnswer;
import pl.exam.app.database.entities.jointables.UserExam;
import pl.exam.app.database.repositories.UserExamRepository;

import javax.annotation.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@ManagedBean
@ViewScoped
public class TakeExamView
{
	private final UserExamRepository userExamRepository;
	private Integer examId;
	private List<Question> shuffledData;
	private Map<Question, Answer> answers = new HashMap<>();

	@Inject
	public TakeExamView(UserExamRepository userExamRepository)
	{
		this.userExamRepository = userExamRepository;

	}

	public void answerChoosen(ValueChangeEvent event)
	{
		Answer choosenAnswer = (Answer) event.getNewValue();
		answers.put(choosenAnswer.getQuestion(), choosenAnswer);
	}

	public void setExamId(Integer examId)
	{
		if (examId != null)
			this.examId = examId;
	}

	public Collection<Question> getShuffledData()
	{
		if(shuffledData == null)
			shuffledData = fetchAndShuffleData();

		return shuffledData;
	}

	private List<Question> fetchAndShuffleData()
	{
		UserExam userExam = userExamRepository.findByKeyExamIdAndKeyUserNickname(examId, getUserNickname());
		List<Question> questions = userExam.getQuestionsWithAnswers().stream()
				.map(QuestionAnswer::getQuestion)
				.collect(Collectors.toList());
		questions.stream()
				.map(Question::getAnswers)
				.forEach(Collections::shuffle);
		return questions;
	}

	public String getUserNickname()
	{
		return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
	}
}
