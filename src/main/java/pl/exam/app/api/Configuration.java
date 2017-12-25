package pl.exam.app.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("api")
public class Configuration extends Application
{
	private final Properties properties = new Properties();

	@PostConstruct
	private void readProperties()
	{
		try (final InputStream inputStream = getClass().getResourceAsStream("/application.properties"))
		{
			properties.load(inputStream);
		} catch (IOException e)
		{
			throw new RuntimeException("Could not init configuration", e);
		}
	}

	@Produces
	public String getProperty(InjectionPoint injectionPoint)
	{
        String key = injectionPoint.getMember().getName();
		return properties.getProperty(key);
	}
	
	@Produces
	public Properties getAllProperties(InjectionPoint injectionPoint)
	{
		return properties;
	}

}
