package pl.exam.app.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import pl.exam.app.business.authentication.control.AuthenticationService;
import pl.exam.app.business.authentication.control.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;
    private final String allowedOrigin;

    public SecurityConfiguration(AuthenticationService authenticationService,
                                 @Value("${allowed.origin}") String allowedOrigin) {
        this.authenticationService = authenticationService;
        this.allowedOrigin = allowedOrigin;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new AuthorizationFilter(authenticationService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlingFilter(allowedOrigin), WebAsyncManagerIntegrationFilter.class)
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll();
    }
}
