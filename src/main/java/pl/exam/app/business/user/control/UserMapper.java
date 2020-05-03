package pl.exam.app.business.user.control;

import pl.exam.app.business.user.boundary.RestUserData;
import pl.exam.app.persistence.user.User;

public class UserMapper {

    public RestUserData toRestData(User user) {
        return RestUserData.builder()
                .username(user.getUsername())
                .build();
    }
}
