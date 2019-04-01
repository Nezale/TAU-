package pl.kozyra.tau.DAO;

import pl.kozyra.tau.Domain.RPGfigure;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    public Optional<T> get (Long id) throws IllegalArgumentException;
    public List<T> getAll();
    public void delete(T o) throws IllegalArgumentException;
    public void update(T o) throws  IllegalArgumentException;
    public void save(T o ) throws IllegalArgumentException;
    public List<T> getByRegex(String regex) throws IllegalArgumentException;

}
