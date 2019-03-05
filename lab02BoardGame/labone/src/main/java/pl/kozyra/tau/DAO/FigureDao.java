package pl.kozyra.tau.DAO;

import pl.kozyra.tau.Domain.RPGfigure;

import java.util.*;

public class FigureDao implements DAO {

    protected Map<Long, RPGfigure> figures;

    @Override
    public Optional get(Long id) throws IllegalArgumentException {
        return Optional.ofNullable(figures.get(id));
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
