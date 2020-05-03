package pl.exam.app.business.user.control;

import org.springframework.stereotype.Service;
import pl.exam.app.business.user.boundary.SchoolClass;
import pl.exam.app.persistence.user.User;
import pl.exam.app.persistence.user.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserMapper userMapper = new UserMapper();
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<SchoolClass> getClassesWithUsers() {
        return userRepository.findAll().stream()
                .collect(Collectors.groupingBy(User::getSchoolClass,
                        Collectors.mapping(userMapper::toRestData, Collectors.toList())))
                .entrySet()
                .stream()
                .map(classUsersEntry -> new SchoolClass(classUsersEntry.getKey(), classUsersEntry.getValue()))
                .collect(Collectors.toList());
    }
}
