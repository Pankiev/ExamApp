package pl.exam.app.jsf.beans.register;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.exam.app.database.entities.Role;
import pl.exam.app.database.repositories.RoleRepository;
import pl.exam.app.database.repositories.UserRepository;

import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@ManagedBean
@ViewScoped
@Component
public class RegisterBean
{
	@Getter
	@Setter
	@Size(min = 5, max = 20, message = "Username should be between 5 and 20 characters")
	private String username;
	@Getter
	@Setter
	@Size(min = 5, max = 30, message = "This message is not showing")
	private String password;
	@Getter
	@Setter
	private String passwordRepeat;

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Inject
	public RegisterBean(UserRepository userRepository, RoleRepository roleRepository)
	{
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public void register()
	{
		UserDetails userDetails = new User(username, password,
				Collections.singleton(new SimpleGrantedAuthority("ROLE_" + "visitor")));
		saveUser(userDetails);
		login(userDetails);
	}

	private void saveUser(UserDetails userDetails)
	{
		pl.exam.app.database.entities.User user = new pl.exam.app.database.entities.User();
		user.setNickname(userDetails.getUsername());
		user.setPassword(userDetails.getPassword());
		Set<Role> roles = userDetails.getAuthorities().stream()
				.map(role -> role.getAuthority().replace("ROLE_", ""))
				.map(roleRepository::findByName)
				.collect(Collectors.toSet());
		user.setRoles(roles);
		userRepository.save(user);
	}

	private void login(UserDetails userDetails)
	{
		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
				userDetails.getPassword(), userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
}