package pl.exam.app.database.repositories;

public interface Repository<PK, T extends Identifiable<PK>>
{
    void save(T entity);
    
    T read(PK key);
}
