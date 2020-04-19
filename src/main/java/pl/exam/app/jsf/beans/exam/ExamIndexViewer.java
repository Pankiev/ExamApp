package pl.exam.app.jsf.beans.exam;

import org.springframework.stereotype.Component;
import pl.exam.app.persistence.exam.Exam;
import pl.exam.app.persistence.exam.ExamRepository;

import javax.annotation.ManagedBean;
import java.util.Collection;
import java.util.Collections;

@ManagedBean
@Component
public class ExamIndexViewer {
    private final ExamRepository examRepository;

    public ExamIndexViewer(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public Collection<Exam> getExams() {
        return Collections.emptyList();
        //return Arrays.asList(examRepository.findAll());
    }

    public Collection<Exam> getLoggedUserExams() {
        return Collections.emptyList();
//        String username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
//        return examRepository.findByUsers_Key_User_Nickname(username);
    }
}
