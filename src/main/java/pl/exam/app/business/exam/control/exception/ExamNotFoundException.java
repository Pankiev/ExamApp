package pl.exam.app.business.exam.control.exception;

public class ExamNotFoundException extends EntityNotFoundException {

    public ExamNotFoundException(Long examId) {
        super("Exam with id " + examId + " not found.");
    }
}
