package pl.exam.app.business.authentication.control;

import lombok.Data;

import java.util.Collection;

@Data
public class UserDetails {
    private final String username;
    private final Collection<String> authorities;

    public UserDetails(String username, Collection<String> authorities) {
        this.username = username;
        this.authorities = authorities;
    }
}
