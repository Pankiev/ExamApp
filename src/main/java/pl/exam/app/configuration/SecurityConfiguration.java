package pl.exam.app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import pl.exam.app.configuration.authentication.AuthenticationService;
import pl.exam.app.configuration.authentication.ExceptionHandlingFilter;
import pl.exam.app.configuration.authentication.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;

    public SecurityConfiguration(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new AuthorizationFilter(authenticationService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlingFilter(), WebAsyncManagerIntegrationFilter.class)
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll();
    }
}
