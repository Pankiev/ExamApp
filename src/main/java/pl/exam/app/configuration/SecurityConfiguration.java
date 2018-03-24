package pl.exam.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	@Autowired
	@CustomAuthentication
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new FakePasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception  
	{ 
        http
            .authorizeRequests()
                .antMatchers("/", "/authentication/**", "/register/**", "/register", "/javax.faces.resource/**", "/css/**",
						"/images/**", "/js/**", "/views/**", "/error/**", "/debug/**", "/views/register/index.xhtml").permitAll()
                .anyRequest().permitAll()
            .and()
            	.formLogin().loginPage("/authentication/login")
            	.usernameParameter("j_username")
            	.passwordParameter("j_password")
            	.failureUrl("/authentication/login?error=true")
                .loginProcessingUrl("/j_spring_security_check")
                .defaultSuccessUrl("/", true)
            .and()
                .logout().logoutSuccessUrl("/")
				.clearAuthentication(true)
				.invalidateHttpSession(true)
            .and()
            	.csrf().disable()
            .exceptionHandling().accessDeniedPage("/denied/index")
            .and()
            	.sessionManagement()
				.maximumSessions(1)
				.maxSessionsPreventsLogin(false);
	}
}