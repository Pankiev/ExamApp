package pl.exam.app.business.exam.control.exception;

public class ExamNotFoundException extends RuntimeException{

    public ExamNotFoundException(Long examId) {
        super("Exam with id " + examId + " not found.");
    }
}
