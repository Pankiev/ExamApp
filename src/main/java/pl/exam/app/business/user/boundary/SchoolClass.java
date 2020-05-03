package pl.exam.app.business.user.boundary;

import lombok.Data;

import java.util.Collection;

@Data
public class SchoolClass {
    private final String name;
    private final Collection<RestUserData> users;
}
