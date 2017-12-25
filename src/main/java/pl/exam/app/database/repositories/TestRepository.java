package pl.exam.app.database.repositories;

import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import pl.exam.app.database.entities.TestEntity;

@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class TestRepository 
{
	@Inject
	private EntityManager entityManger;
	
	public void saveSomething()
	{
		entityManger.getTransaction().begin();
		TestEntity testEntity = new TestEntity();
		testEntity.setTestString("Some test string");
		entityManger.persist(testEntity);
		entityManger.getTransaction().commit();
	}
	
}
