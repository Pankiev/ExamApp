package pl.exam.app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.exam.app.business.authentication.control.HeaderUserDetailsArgumentResolver;

import java.util.List;

@Configuration
@EnableWebMvc
public class RestConfiguration implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new HeaderUserDetailsArgumentResolver());
    }
}
