package pl.exam.app;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class TestTest
{
	@PostConstruct
	public void x()
	{
		System.out.println("asdasd");
	}
}
