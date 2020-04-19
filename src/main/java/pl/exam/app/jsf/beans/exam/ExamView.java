package pl.exam.app.jsf.beans.exam;

import lombok.Getter;
import lombok.Setter;
import pl.exam.app.persistence.exam.Exam;
import pl.exam.app.persistence.user.User;
import pl.exam.app.persistence.userexam.UserExam;
import pl.exam.app.persistence.exam.ExamRepository;
import pl.exam.app.persistence.userexam.UserExamRepository;
import pl.exam.app.persistence.user.UserRepository;
import pl.exam.app.jsf.beans.helpers.Dictionary;

import javax.annotation.ManagedBean;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ManagedBean
public class ExamView {
    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final UserExamRepository userExamRepository;
    private final Dictionary dictionary;
    private Integer examId;
    private Collection<User> selectedUsers = Collections.emptyList();
    @Getter
    @Setter
    private String selectedClass;

    public ExamView(UserRepository userRepository, ExamRepository examRepository, UserExamRepository userExamRepository,
                    Dictionary dictionary) {
        this.userRepository = userRepository;
        this.examRepository = examRepository;
        this.userExamRepository = userExamRepository;
        this.dictionary = dictionary;
    }

    public Iterable<String> getSchoolClasses() {
        return userRepository.findDistinctSchoolClasses();
    }

    public void onClassChange() {
        selectedUsers = userRepository.findBySchoolClass(selectedClass);
    }

    public Collection<User> getSelectedUsers() {
        return selectedUsers;
    }

    public void setExamId(Integer examId) {
        if (examId != null)
            this.examId = examId;
    }

    public void assignUsersToExam() {
        Set<UserExam> newRecords = getNewRecords();
        userExamRepository.saveAll(newRecords);
        showSavedMessage();
    }

    private Set<UserExam> getNewRecords() {
        Exam exam = examRepository.findById(examId).get();
        Set<UserExam> userExams = exam.getUsers();
        Set<User> currentUsers = userExams.stream().map(UserExam::getUser)
                .collect(Collectors.toSet());
        Set<UserExam> newRecords = new HashSet<>();
        for (User user : selectedUsers)
            if (!currentUsers.contains(user))
                newRecords.add(new UserExam(user, exam));
        return newRecords;
    }

    private void showSavedMessage() {
        //String saved = dictionary.getMessage("Users.saved");
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, saved, ""));
    }
}
