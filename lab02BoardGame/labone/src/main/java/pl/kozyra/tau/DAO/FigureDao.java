package pl.kozyra.tau.DAO;

import java.util.List;
import java.util.Optional;

public class FigureDao implements DAO {

    @Override
    public Optional get(Long id) throws IllegalArgumentException {
        return Optional.empty();
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public void save(Object o) {

    }

    @Override
    public void delete(Object o) throws IllegalArgumentException {

    }

    @Override
    public void update(Object o) throws IllegalArgumentException {

    }
}
