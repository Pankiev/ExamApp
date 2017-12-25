package pl.exam.app.database;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import pl.exam.app.database.entities.TestEntity;

@Singleton
public class HibernateUtil
{
	@Inject
	private Properties allProperties;

	private SessionFactory sessionFactory;

	@PostConstruct
	public void initialize()
	{
		sessionFactory = createSessionFactory();
	}

	private SessionFactory createSessionFactory()
	{
		try
		{
			Configuration configuration = new Configuration();
			Properties props = getHibernateProperties();
			configuration.setProperties(props)
			.addAnnotatedClass(TestEntity.class);

			System.out.println("Hibernate Java Config serviceRegistry created");

			SessionFactory sessionFactory = configuration.buildSessionFactory();

			return sessionFactory;
		} catch (Throwable ex)
		{
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	private Properties getHibernateProperties()
	{
		Properties properties = new Properties();
		allProperties.entrySet().stream().filter(entry -> ((String) entry.getKey()).startsWith("hibernate"))
				.forEach(entry -> properties.setProperty((String) entry.getKey(), (String) entry.getValue()));
		return properties;
	}

	@Produces
	public EntityManager createEntityManager()
	{
		return sessionFactory.createEntityManager();
	}
}
