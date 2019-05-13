package pl.tau.boardgame.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.tau.boardgame.domain.Figure;
import pl.tau.boardgame.domain.Owner;

import java.util.List;

@Component
@Transactional
public class FigureManagerImpl implements FigureManager {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long addFigure(Figure figure) {
        if (figure.getId() != null)
            throw new IllegalArgumentException("the person ID should be null if added to database");
        sessionFactory.getCurrentSession().persist(figure);
        sessionFactory.getCurrentSession().flush();
        return figure.getId();
    }

    @Override
    public List<Figure> findAllFigure() {
        return sessionFactory.getCurrentSession().getNamedQuery("figure.all")
                .list();
    }

    @Override
    public Figure findFigureById(Long id) {
        return (Figure) sessionFactory.getCurrentSession().get(Figure.class, id);
    }

    @Override
    public void deleteFigure(Figure figure) {
        figure = (Figure) sessionFactory.getCurrentSession().get(Figure.class,
                figure.getId());
        sessionFactory.getCurrentSession().delete(figure);
    }

    @Override
    public void updateFigure(Figure figure) {
        sessionFactory.getCurrentSession().update(figure);
    }

    @Override
    public List<Figure> findFigureByName(String figureName) {
        return (List<Figure>) sessionFactory.getCurrentSession()
                .getNamedQuery("figure.findFigure")
                .setString("figureNameFragment", "%" + figureName + "%")
                .list();
    }

    @Override
    public Long addOnwer(Owner owner) {
        if (owner.getId() != null)
            throw new IllegalArgumentException("the owner ID should be null if added to database");
        sessionFactory.getCurrentSession().persist(owner);
        for (Figure figure : owner.getFigures()) {
            figure.setOwner(owner);
            sessionFactory.getCurrentSession().update(figure);
        }
        sessionFactory.getCurrentSession().flush();
        return owner.getId();
    }

    @Override
    public List<Figure> getAllFiguresForOwner(Owner owner) {
        return (List<Figure>) sessionFactory.getCurrentSession()
                .getNamedQuery("figure.findFiguresByOwner")
                .setParameter("owner", owner)
                .list();
    }

    @Override
    public Owner findOwnerById(Long id) {
        return (Owner) sessionFactory.getCurrentSession().get(Owner.class, id);
    }

    @Override
    public void transferFigureToAnotherOwner(Figure figure1, Figure figure2, Owner owner1, Owner owner2) {
        figure1.setOwner(owner2);
        sessionFactory.getCurrentSession().save(owner2);
        sessionFactory.getCurrentSession().save(figure1);

        figure2.setOwner(owner1);
        sessionFactory.getCurrentSession().save(owner1);
        sessionFactory.getCurrentSession().save(figure2);
    }
}
