package pl.labfive.boardGame.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.labfive.boardGame.domain.RPGFigure;

@Component
@Transactional
public class FigureMangerHibernateImpl implements FigureManager {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Long addFigure(RPGFigure RPGFigure) {
		return (Long) sessionFactory.getCurrentSession().save(RPGFigure);
	}

	@Override
	public void updateFigure(RPGFigure RPGFigure) {
        sessionFactory.getCurrentSession().update(RPGFigure);
	}

	@Override
	public RPGFigure findFigureById(Long id) {
		return sessionFactory.getCurrentSession().get(RPGFigure.class, id);
	}

	@Override
	public void deleteFigure(RPGFigure RPGFigure) {
	    sessionFactory.getCurrentSession().delete(RPGFigure);
	}

	@Override
	public List<RPGFigure> findAllFigures() {
		return sessionFactory.getCurrentSession().getNamedQuery("figure.all")
				.list();
	}

	@Override
	public List<RPGFigure> findFiguresByName(String name) {
		return (List<RPGFigure>) sessionFactory.getCurrentSession()
				.getNamedQuery("figure.findFigureByName")
				.setString("name", "%"+name+"%")
				.list();
	}


}
