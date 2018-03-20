package pl.exam.app.jsf.beans.exam;

import pl.exam.app.database.entities.Answer;
import pl.exam.app.database.entities.Question;
import pl.exam.app.database.entities.QuestionAnswer;
import pl.exam.app.database.entities.jointables.UserExam;
import pl.exam.app.database.repositories.UserExamRepository;
import pl.exam.app.exceptions.NoSuchAnswerException;

import javax.annotation.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@ManagedBean
@ViewScoped
public class TakeExamView
{
	private final UserExamRepository userExamRepository;
	private Integer examId;
	private List<Question> shuffledData;
	private Map<Question, Answer> answers = new HashMap<>();
	private UserExam userExam;

	@Inject
	public TakeExamView(UserExamRepository userExamRepository)
	{
		this.userExamRepository = userExamRepository;
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
		userExam = userExamRepository.findByKeyExamIdAndKeyUserNickname(examId, getUserNickname());
		List<Question> questions = userExam.getQuestionsWithAnswers().stream()
				.map(QuestionAnswer::getQuestion)
				.collect(Collectors.toList());
		questions.stream()
				.map(Question::getAnswers)
				.forEach(Collections::shuffle);
		return questions;
	}

	private String getUserNickname()
	{
		return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
	}

	public void answerSelected(ValueChangeEvent event)
	{
		Integer answerId = Integer.valueOf((String) event.getNewValue());
		Answer answer = findAnswer(answerId);
		answers.put(answer.getQuestion(), answer);
	}

	private Answer findAnswer(Integer answerId)
	{
		return shuffledData.stream()
				.map(Question::getAnswers)
				.flatMap(Collection::stream)
				.filter(answer -> answer.getId().equals(answerId))
				.findAny()
				.orElseThrow(NoSuchAnswerException::new);
	}

	public void submitExam()
	{
		userExam.getQuestionsWithAnswers().forEach(this::setChoosenAnswer);
		userExam.setFinished(true);
		userExam.setTotalScore(calculateTotalScore(userExam.getQuestionsWithAnswers()));
		userExamRepository.save(userExam);
	}

	private Float calculateTotalScore(Set<QuestionAnswer> questionsWithAnswers)
	{
		return (float) questionsWithAnswers.stream()
				.map(QuestionAnswer::getAnswer)
				.filter(Objects::nonNull)
				.filter(Answer::getValid)
				.count() / (float)questionsWithAnswers.size();
	}

	private void setChoosenAnswer(QuestionAnswer questionAnswer)
	{
		questionAnswer.setAnswer(answers.get(questionAnswer.getQuestion()));
	}
}
