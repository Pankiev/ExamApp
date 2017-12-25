package pl.exam.app.views;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pl.exam.app.database.repositories.TestRepository;

@ApplicationScoped
@Named("welcomeView")
public class WelcomeView
{
	@Inject
	private TestRepository testRepostiory;

	public void saveSth()
	{
		testRepostiory.saveSomething();
	}
}

