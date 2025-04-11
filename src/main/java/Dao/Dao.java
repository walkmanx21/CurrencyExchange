package Dao;

import java.util.List;
import java.util.Optional;

public interface Dao <K, T> {
    List<T> findAllEntities ();
    Optional<T> findEntity (K code);
    T create (T entity);
    void update (T entity);
}
