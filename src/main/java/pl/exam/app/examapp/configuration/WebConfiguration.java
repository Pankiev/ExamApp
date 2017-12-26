package pl.exam.app.examapp.configuration;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfiguration extends WebMvcConfigurerAdapter implements ServletContextAware
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
	public ServletRegistrationBean facesServlet()
	{
		ServletRegistrationBean servletRegisterationBean = new ServletRegistrationBean();
		servletRegisterationBean.addUrlMappings("/faces/*", "*.xhtml");
		servletRegisterationBean.setLoadOnStartup(1);
		servletRegisterationBean.setServlet(new FacesServlet());
		return servletRegisterationBean;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("/resoruces/**").addResourceLocations("/resources/");
	}

	@Override
	public void setServletContext(ServletContext servletContext)
	{
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
	}

}
