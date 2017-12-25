package pl.exam.app.database.repositories;

import java.util.function.Supplier;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import lombok.Getter;

public abstract class HibernateRepository<PK, T extends Identifiable<PK>> implements Repository<PK, T>
{
    @Inject
    @Getter
    private EntityManager entityManager;
    
    @Override
    public void save(T entity)
    {
        executeInTransaction( () -> entityManager.persist(entity));
    }

    protected <Q> Q executeInTransaction(Supplier<Q> runnable)
    {
        entityManager.getTransaction().begin();
        Q result = runnable.get();
        entityManager.getTransaction().commit();
        return result;
    }
    
    protected void executeInTransaction(Runnable runnable)
    {
        entityManager.getTransaction().begin();
        runnable.run();
        entityManager.getTransaction().commit();
    }
    
}
