package pl.kozyra.tau.DAO;

import pl.kozyra.tau.Domain.RPGfigure;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FigureDao implements DAO<RPGfigure> {

    protected List<RPGfigure> figures = new ArrayList<>();

    @Override
    public Optional<RPGfigure> get(Long id) throws IllegalArgumentException {

        if (figures.stream().noneMatch(figure -> figure.getId().equals(id)))
            throw new IllegalArgumentException("Key does not exist");

        return figures.stream().filter(figure -> figure.getId().equals(id)).findFirst();
    }

    @Override
    public List<RPGfigure> getAll() {
        return figures;
    }

    public List<RPGfigure> getFigures() {
        return figures;
    }

    public void setFigures(List<RPGfigure> figures) {
        this.figures = figures;
    }

    @Override
    public void save(RPGfigure o) throws IllegalArgumentException {
        if(figures.stream().anyMatch(figure -> figure.getId().equals(o.getId())))
            throw  new IllegalArgumentException("Figure with this id already exist");
        figures.add(o);
    }

    @Override
    public List<RPGfigure> getByRegex(String regex) throws IllegalArgumentException {
            List<RPGfigure> ret = new LinkedList<>();
            Pattern pattern = Pattern.compile(regex);
            for(RPGfigure f : figures) {
                Matcher matcher = pattern.matcher(f.getName());
                if (matcher.find())
                    ret.add(f);
            }
            return ret;

    }

    @Override
    public void delete(RPGfigure o) throws IllegalArgumentException {
        if (figures.stream().noneMatch(figure -> figure.getId().equals(o.getId())))
            throw new IllegalArgumentException("Key does not exist");
        figures.remove(o.getId().intValue());
    }

    @Override
    public void update(RPGfigure o) throws IllegalArgumentException {

        if (figures.stream().noneMatch(figure -> figure.getId().equals(o.getId())))
            throw new IllegalArgumentException("Key does not exist");
        figures.add(o.getId().intValue() -1, o);

    }
}
