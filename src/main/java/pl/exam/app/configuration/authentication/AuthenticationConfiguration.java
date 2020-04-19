package pl.exam.app.configuration.authentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.exam.app.persistence.role.Role;
import pl.exam.app.persistence.user.User;
import pl.exam.app.persistence.user.UserRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.LinkedList;

@Configuration
@CustomAuthentication
public class AuthenticationConfiguration implements UserDetailsService {
    private final UserRepository userService;

    public AuthenticationConfiguration(UserRepository userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User user = userService.findByNickname(nickname);
        if (user == null)
            throw new UsernameNotFoundException(nickname);
        Collection<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(user);

        return new org.springframework.security.core.userdetails.User(user.getNickname(), user.getPassword(),
                grantedAuthorities);
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(User user) {
        Collection<GrantedAuthority> grantedAuthorities = new LinkedList<>();
        for (Role role : user.getRoles())
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));

        return grantedAuthorities;
    }
}
