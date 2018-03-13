package pl.exam.app.examapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception  
	{ 
        http
            .authorizeRequests()
                .antMatchers("/", "/authentication/**", "/javax.faces.resource/**", "/resources/**", "/error/**", "/debug/**", "/css/**", "/images/**").permitAll()
                //.antMatchers("/exam-event/**").hasAnyRole("admin", "student")
                //.anyRequest().authenticated()
                .anyRequest().permitAll()
//            .and()
//            	.formLogin().loginPage("/authentication/login")
//            	.usernameParameter("j_username")
//            	.passwordParameter("j_password")
//            	.failureUrl("/authentication/login?error=true")
//                .loginProcessingUrl("/j_spring_security_check")
//                .defaultSuccessUrl("/") 
            .and()
                .logout().logoutSuccessUrl("/")
            .and()
            	.csrf().disable() 
            .exceptionHandling().accessDeniedPage("/denied/index")
            .and()
            	.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
            
	}
}