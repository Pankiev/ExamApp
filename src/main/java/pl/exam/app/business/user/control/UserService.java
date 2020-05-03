package pl.exam.app.business.user.control;

import org.springframework.stereotype.Service;
import pl.exam.app.business.authentication.control.UserDetails;
import pl.exam.app.business.exam.boundary.RestUserExamData;
import pl.exam.app.business.user.boundary.RestUserData;
import pl.exam.app.business.user.boundary.SchoolClass;
import pl.exam.app.business.userexam.control.UserExamMapper;
import pl.exam.app.persistence.user.User;
import pl.exam.app.persistence.user.UserRepository;
import pl.exam.app.persistence.userexam.UserExamRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserExamMapper userExamMapper = new UserExamMapper();
    private final UserMapper userMapper = new UserMapper();
    private final UserRepository userRepository;
    private final UserExamRepository userExamRepository;

    public UserService(UserRepository userRepository, UserExamRepository userExamRepository) {
        this.userRepository = userRepository;
        this.userExamRepository = userExamRepository;
    }

    public Collection<SchoolClass> getClassesWithUsers(UserDetails userDetails) {
        return userRepository.findAll().stream()
                .collect(Collectors.groupingBy(User::getSchoolClass,
                        Collectors.mapping(userMapper::toRestData, Collectors.toList())))
                .entrySet()
                .stream()
                .map(classUsersEntry -> new SchoolClass(classUsersEntry.getKey(), classUsersEntry.getValue()))
                .collect(Collectors.toList());
    }

    public Collection<RestUserData> getUsers(UserDetails userDetails) {
        return userRepository.findAll().stream()
                .map(userMapper::toRestData)
                .collect(Collectors.toList());
    }

    public Collection<RestUserExamData> getUserExams(UserDetails userDetails, Long userId) {
        return userExamRepository.findByKeyUserId(userId).stream()
                .map(userExamMapper::toRestData)
                .collect(Collectors.toList());
    }
}
