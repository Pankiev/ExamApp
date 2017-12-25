package pl.exam.app.database.repositories;

import javax.persistence.EntityManager;

public interface EntityManagerAction
{
    void execute(EntityManager entityMangaer);
}
