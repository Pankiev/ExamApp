package pl.exam.app.examapp.configuration;

import org.primefaces.push.PushServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfiguration extends WebMvcConfigurerAdapter
{
	@Bean
	public ViewResolver viewResolver()
	{
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/views/");
		viewResolver.setSuffix(".xhtml");

		return viewResolver;
	}

	@Bean
	public ServletRegistrationBean pushServletRegistration()
	{
		ServletRegistrationBean pushServlet = new ServletRegistrationBean(new PushServlet(), "/primepush/*");
		pushServlet.addInitParameter("org.atmosphere.annotation.packages", "org.primefaces.push");
	    pushServlet.addInitParameter("org.atmosphere.cpr.packages", "pl.exam.app.examapp");
	    pushServlet.setAsyncSupported(true);
	    pushServlet.setLoadOnStartup(0);
	    pushServlet.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return pushServlet;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
}
