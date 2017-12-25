package pl.exam.app.database.repositories;

import javax.inject.Singleton;
import javax.persistence.Query;

import pl.exam.app.database.entities.TestEntity;

@Singleton
public class NonCachedTestRepository extends HibernateRepository<Integer, TestEntity>
{ 
    @Override
    public void save(TestEntity entity)
    {
        executeInTransaction(() -> getEntityManager().persist(entity));
    }

    @Override
    public TestEntity read(Integer key)
    {
        return executeInTransaction(() -> doRead(key));
    }

    private TestEntity doRead(Integer key)
    {
        Query query = getEntityManager().createQuery("FROM TestEntity t WHERE t.id=" + key);
        return (TestEntity) query.getSingleResult();
    }    
}
