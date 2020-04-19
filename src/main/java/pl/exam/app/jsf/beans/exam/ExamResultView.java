package pl.exam.app.jsf.beans.exam;

import pl.exam.app.persistence.userexam.UserExam;
import pl.exam.app.persistence.userexam.UserExamRepository;

import javax.annotation.ManagedBean;
import java.util.ArrayList;
import java.util.Collection;

@ManagedBean
public class ExamResultView {
    private final UserExamRepository userExamRepository;
    private final Collection<UserExam> userExams = new ArrayList<>();
    private Integer examId;

    public ExamResultView(UserExamRepository userExamRepository) {
        this.userExamRepository = userExamRepository;
    }

    public void setExamId(Integer examId) {
        if (examId != null)
            this.examId = examId;
    }

    public UserExam getUserExamResult() {
        return userExamRepository.findByKeyExamIdAndKeyUserUsername(examId, getUsername());
    }

    private String getUsername() {
        return "nullUsername";
        //return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    }

    public Collection<UserExam> getUsersAssignedToExamData() {
        if (userExams.isEmpty())
            userExams.addAll(userExamRepository.findByKey_Exam_Id(examId));
        return userExams;
    }
}
