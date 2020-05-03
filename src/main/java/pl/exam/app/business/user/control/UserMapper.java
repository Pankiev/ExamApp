package pl.exam.app.business.user.control;

import pl.exam.app.business.user.boundary.RestUserData;
import pl.exam.app.persistence.user.User;

public class UserMapper {

    public RestUserData toRestData(User user) {
        return RestUserData.builder()
                .id(user.getId())
                .username(user.getUsername())
                .creationDate(user.getCreationDate())
                .idInClass(user.getIdInClass())
                .schoolClass(user.getSchoolClass())
                .build();
    }
}
