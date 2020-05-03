package pl.exam.app.business.exam.control.exception;

public class UserExamNotFoundException extends EntityNotFoundException {
    public UserExamNotFoundException(String username, Long examId) {
        super("User " + username + " did not approach exam with id: " + examId);
    }
}
