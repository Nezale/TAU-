package pl.kozyra.boardGame.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.kozyra.boardGame.domain.Owner;
import pl.kozyra.boardGame.domain.RPGFigure;

import java.util.List;

@Component
@Transactional
public class RPGFigureManagerHibernateImpl implements RPGFigureManager {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long addRPGFigurejkk(RPGFigure RPGFigure) {
        // Pierwsza wersja dodawania do bazy danych - korzystamy z persist - to jest dostepne w JPA
        if (RPGFigure.getId() != null)
            throw new IllegalArgumentException("the person ID should be null if added to database");
        sessionFactory.getCurrentSession().persist(RPGFigure);
        sessionFactory.getCurrentSession().flush();
        return RPGFigure.getId();
    }

    @Override
    public List<RPGFigure> findAllRPGFigure() {
        return sessionFactory.getCurrentSession().getNamedQuery("RPGFigure.all")
                .list();
    }

    @Override
    public RPGFigure findRPGFigureById(Long id) {
        return sessionFactory.getCurrentSession().get(RPGFigure.class, id);
    }

    @Override
    public void deleteRPGFigure(RPGFigure RPGFigure) {
        RPGFigure = (RPGFigure) sessionFactory.getCurrentSession().get(RPGFigure.class,
                RPGFigure.getId());
        sessionFactory.getCurrentSession().delete(RPGFigure);

    }

    @Override
    public void updateRPGFigure(RPGFigure RPGFigure) {
        sessionFactory.getCurrentSession().update(RPGFigure);

    }
    @Override
    public List<RPGFigure> findRPGFigureByName(String RPGFigureName) {
        return (List<RPGFigure>) sessionFactory.getCurrentSession()
                .getNamedQuery("RPGFigure.findRPGFigureByName")
                .setString("modelNameFragment", "%"+RPGFigureName+"%")
                .list();
    }

    @Override
    public List<RPGFigure> getAllRPGFiguresForOwner(Owner owner) {
        return (List<RPGFigure>) sessionFactory.getCurrentSession()
                .getNamedQuery("RPGFigure.findRPGFiguresByOwner")
                .setParameter("owner", owner)
                .list();
    }

    @Override
    public Long addOwner(Owner owner) {
        if (owner.getId() != null)
            throw new IllegalArgumentException("the director ID should be null if added to database");
        sessionFactory.getCurrentSession().persist(owner);
        for (RPGFigure RPGFigure : owner.getRPGFigures()) {
            RPGFigure.setOwner(owner);
            sessionFactory.getCurrentSession().update(RPGFigure);
        }
        sessionFactory.getCurrentSession().flush();
        return owner.getId();
    }

    @Override
    public Owner findOwnerById(Long id) {
        return (Owner) sessionFactory.getCurrentSession().get(Owner.class, id);
    }

    @Override
    public void transferRPGFigureToAnotherOwner(RPGFigure RPGFigure1, RPGFigure RPGFigure2, Owner owner1, Owner owner2) {

        RPGFigure1.setOwner(owner2);
        sessionFactory.getCurrentSession().save(owner2);
        sessionFactory.getCurrentSession().save(RPGFigure1);

        RPGFigure2.setOwner(owner1);
        sessionFactory.getCurrentSession().save(owner1);
        sessionFactory.getCurrentSession().save(RPGFigure2);
    }

}