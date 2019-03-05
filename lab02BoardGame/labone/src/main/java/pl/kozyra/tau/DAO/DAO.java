package pl.kozyra.tau.DAO;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    public Optional<T> get (Long id) throws IllegalArgumentException;
    public List<T> getAll();
    public void save(T o);
    public void delete(Long id) throws IllegalArgumentException;
    public void update(T o) throws  IllegalArgumentException;

}
