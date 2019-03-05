package pl.kozyra.tau.DAO;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> get (Long id) throws IllegalArgumentException;
    List<T> getAll();
    void save(T o);
    void delete(T o) throws IllegalArgumentException;
    void update(T o) throws  IllegalArgumentException;

}
