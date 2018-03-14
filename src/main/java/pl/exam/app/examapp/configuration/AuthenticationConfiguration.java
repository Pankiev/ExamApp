package pl.exam.app.examapp.configuration;

import java.util.Collection;
import java.util.LinkedList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pl.exam.app.examapp.database.entities.Role;
import pl.exam.app.examapp.database.entities.User;
import pl.exam.app.examapp.database.repositories.UserRepository;

@Configuration
@CustomAuthentication
public class AuthenticationConfiguration implements UserDetailsService
{
	@Autowired
	private UserRepository userService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException
	{
		User user = userService.findByNickname(nickname); 
		if(user == null)
			throw new UsernameNotFoundException(nickname);
		Collection<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(user);
		
		return new org.springframework.security.core.userdetails.User(user.getNickname(), user.getPassword(),
				grantedAuthorities);
	}

	private Collection<GrantedAuthority> getGrantedAuthorities(User user)
	{
		Collection<GrantedAuthority> grantedAuthorities = new LinkedList<>();
		for (Role role : user.getRoles())
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));

		return grantedAuthorities;
	}
}
