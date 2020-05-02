package pl.exam.app.jsf.beans.exam;

import pl.exam.app.persistence.Answer;
import pl.exam.app.persistence.question.Question;
import pl.exam.app.persistence.QuestionAnswer;
import pl.exam.app.persistence.userexam.UserExam;
import pl.exam.app.persistence.userexam.UserExamRepository;
import pl.exam.app.jsf.beans.exam.timer.AsyncTimer;

import javax.annotation.ManagedBean;
import java.util.*;
import java.util.stream.Collectors;

@ManagedBean
public class TakeExamView {
    private final UserExamRepository userExamRepository;
    private final AsyncTimer asyncTimer;
    private Long examId;
    private List<Question> shuffledData;
    private final Map<Question, Answer> answers = new HashMap<>();
    private UserExam userExam;
    private double timeForExam;
    private double timeLeft;

    public TakeExamView(UserExamRepository userExamRepository, AsyncTimer asyncTimer) {
        this.userExamRepository = userExamRepository;
        this.asyncTimer = asyncTimer;
    }

    public void setExamId(Long examId) {
        if (examId != null && this.examId == null) {
            this.examId = examId;
            initialize(examId);
        }
    }

    private void initialize(Long examId) {
        userExam = userExamRepository.findByKeyExamIdAndKeyUserUsername(examId, getUserNickname()).orElseThrow();
        this.timeForExam = calculateTimeForExam(userExam);
        timerCycle();
        if (timeLeft <= 0)
            submitExam();
        else
            startMeasuringTime();
    }

    private double calculateTimeForExam(UserExam userExam) {
        return userExam.getQuestionsWithAnswers().stream()
                .map(QuestionAnswer::getQuestion)
                .mapToInt(Question::getSecondsForAnswer)
                .sum();
    }

    private void startMeasuringTime() {
        asyncTimer.startTimer(timeForExam, time -> timerCycle());
    }

    private void timerCycle() {
        updateTime();
        submitExamIfTimePassed();
    }

    private void updateTime() {
        Date now = new Date();
        long millisecondsLeft = now.getTime() - userExam.getTestApproachDate().getTime();
        double timePassed = millisecondsLeft / 1000.0f;
        this.timeLeft = this.timeForExam - timePassed;
    }

    private void submitExamIfTimePassed() {
        if (timeLeft <= 0)
            submitExam();
    }

    public Collection<Question> getShuffledData() {
        if (shuffledData == null)
            shuffledData = fetchAndShuffleData();

        return shuffledData;
    }

    private List<Question> fetchAndShuffleData() {
        List<Question> questions = userExam.getQuestionsWithAnswers().stream()
                .map(QuestionAnswer::getQuestion)
                .collect(Collectors.toList());
        questions.stream()
                .map(Question::getAnswers)
                .forEach(Collections::shuffle);
        return questions;
    }

    private String getUserNickname() {
        return "NullUsernickname";
        //return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    }

    public void answerSelected(Long answerId) {
        Answer answer = findAnswer(answerId);
        answers.put(answer.getQuestion(), answer);
    }

    private Answer findAnswer(Long answerId) {
        return shuffledData.stream()
                .map(Question::getAnswers)
                .flatMap(Collection::stream)
                .filter(answer -> answer.getId().equals(answerId))
                .findAny()
                .orElseThrow(NoSuchAnswerException::new);
    }

    public void submitExam() {
        userExam.getQuestionsWithAnswers().forEach(this::setChosenAnswer);
        userExam.setFinished(true);
        userExam.setTotalScore(calculateTotalScore(userExam.getQuestionsWithAnswers()));
        userExamRepository.save(userExam);
    }

    private Float calculateTotalScore(Set<QuestionAnswer> questionsWithAnswers) {
        return (float) questionsWithAnswers.stream()
                .map(QuestionAnswer::getAnswer)
                .filter(Objects::nonNull)
                .filter(Answer::isValid)
                .count() / (float) questionsWithAnswers.size();
    }

    private void setChosenAnswer(QuestionAnswer questionAnswer) {
        questionAnswer.setAnswer(answers.get(questionAnswer.getQuestion()));
    }

    public int getTimeLeft() {
        return (int) timeLeft;
    }
}
