package pl.exam.app.views;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pl.exam.app.database.entities.TestEntity;
import pl.exam.app.database.repositories.Repository;

@ApplicationScoped
@Named("welcomeView")
public class WelcomeView
{
	@Inject
	private Repository<Integer, TestEntity> testRepostiory;

	public void saveSth()
	{
        TestEntity testEntity = new TestEntity();
        testEntity.setTestString("Some test string");
		testRepostiory.save(testEntity);
		TestEntity read = testRepostiory.read(1);
		read.setTestString("asd");
		System.out.println(read);
	}
}

