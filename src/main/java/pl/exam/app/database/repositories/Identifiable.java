package pl.exam.app.database.repositories;

public interface Identifiable<PK>
{
    PK getId();
    
    void setId(PK id);
}
