package pl.kozyra.tau.DAO;

import pl.kozyra.tau.Domain.RPGfigure;

import java.util.*;
import java.util.stream.Collectors;

public class FigureDao implements DAO<RPGfigure> {

    protected Map<Long, RPGfigure> figures;

    @Override
    public Optional<RPGfigure> get(Long id) throws IllegalArgumentException {

        if (!figures.containsKey(id))
            throw new IllegalArgumentException("Key does not exist");

        return Optional.ofNullable(figures.get(id));
    }

    @Override
    public List<RPGfigure> getAll() {
        return figures.entrySet().stream().map(x->x.getValue()).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException {
        if (!figures.containsKey(id))
            throw new IllegalArgumentException("Key does not exist");
        figures.remove(id);
    }

    @Override
    public void update(RPGfigure o) throws IllegalArgumentException {

        if (!figures.containsKey(o.getId()))
            throw new IllegalArgumentException("Key does not exist");
        figures.put(o.getId(),o);

    }
}
