package pl.exam.app.business.exam.control.exception;

public class AnswerNotFoundException extends EntityNotFoundException {

    public AnswerNotFoundException(Long answerId) {
        super("Answer with id " + answerId + " not found.");
    }
}
