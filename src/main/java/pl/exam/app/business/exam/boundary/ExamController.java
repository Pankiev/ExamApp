package pl.exam.app.business.exam.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.exam.app.business.ErrorEntity;
import pl.exam.app.business.authentication.control.UserDetails;
import pl.exam.app.business.exam.control.ExamService;
import pl.exam.app.business.exam.control.exception.EntityNotFoundException;
import pl.exam.app.business.exam.control.exception.TakeExamException;

import java.util.Collection;

@RestController
@RequestMapping("/exam")
public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping({"", "/", "/index"})
    public Collection<RestExamData> examIndex(UserDetails userDetails) {
        return examService.findAll(userDetails);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public RestExamData examCreate(UserDetails userDetails, @RequestBody RestExamData request) {
        return examService.createExam(userDetails, request);
    }

    @GetMapping("/{id}/userResult")
    public RestUserExamData getUserExamResultDetails(UserDetails userDetails, @PathVariable("id") Long examId) {
        return examService.getUserExamResultDetails(userDetails, examId);
    }

    @PostMapping("/{id}/takeTest")
    public RestUserExamData takeTest(UserDetails userDetails, @PathVariable("id") Long examId) {
        return examService.takeTest(userDetails, examId);
    }

    @PostMapping("/chooseAnswer/{answerId}")
    public void chooseAnswer(UserDetails userDetails, @PathVariable("answerId") Long answerId) {
        examService.chooseActiveTestAnswer(userDetails, answerId);
    }

    @PostMapping("/unchooseAnswer/{answerId}")
    public void unchooseAnswer(UserDetails userDetails, @PathVariable("answerId") Long answerId) {
        examService.unchooseActiveTestAnswer(userDetails, answerId);
    }

    @PostMapping("/{id}/submit")
    public RestUserExamData submitTest(UserDetails userDetails, @PathVariable("id") Long examId) {
        return examService.submitTest(userDetails, examId);
    }

    @GetMapping("/{id}/allApproaches")
    public Collection<RestUserExamData> getAllApproaches(UserDetails userDetails, @PathVariable("id") Long examId) {
        return examService.getExamApproaches(examId);
    }

    @ExceptionHandler
    private ErrorEntity handleNotFoundException(EntityNotFoundException e) {
        return ErrorEntity.notFound(e.getMessage());
    }

    @ExceptionHandler
    private ErrorEntity handleTakeExamException(TakeExamException e) {
        return ErrorEntity.forbidden(e.getMessage());
    }
}
