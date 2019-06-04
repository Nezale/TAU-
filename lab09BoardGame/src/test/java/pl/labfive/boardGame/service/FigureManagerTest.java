package pl.labfive.boardGame.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.labfive.boardGame.domain.RPGFigure;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@Rollback
@Commit
@Transactional(transactionManager = "txManager")
public class FigureManagerTest {

	@Autowired
	FigureManager figureManager;

	List<Long> figureIds;
	List<Long> clientIds;

	/**
	 * Helper method allowing for easier adding books to tests
	 * @param name
	 * @param HP
	 * @return
	 */
	private RPGFigure addBookHelper(String name, int HP) {
		Long figureId;
		RPGFigure RPGFigure;
		RPGFigure = new RPGFigure();
		RPGFigure.setName(name);
		RPGFigure.setHP(HP);
		RPGFigure.setId(null);
		figureIds.add(figureId = figureManager.addFigure(RPGFigure));
		assertNotNull(figureId);
		return RPGFigure;
	}


	@Before
	public void setup() {
		figureIds = new LinkedList<>();
		clientIds = new LinkedList<>();

		// first RPGFigures
		addBookHelper("skeleton",200);
		addBookHelper("golem",300);
		RPGFigure RPGFigure = addBookHelper("something",500);

	}

	@Test
	public void addFigureTest() {

		assertTrue(figureIds.size() > 0);
	}

	@Test
	public void getAllFiguresTest() {
		List <Long> foundIds = new LinkedList<>();
		for (RPGFigure RPGFigure : figureManager.findAllFigures()) {
			if (figureIds.contains(RPGFigure.getId())) foundIds.add(RPGFigure.getId());
		}
		assertEquals(figureIds.size(), foundIds.size());
	}


	@Test
	public void getFigureById() {
		RPGFigure RPGFigure = figureManager.findFigureById(figureIds.get(0));
		assertEquals("skeleton", RPGFigure.getName());
	}

	@Test
	public void deleteFigureTest() {
		int prevSize = figureManager.findAllFigures().size();
		RPGFigure RPGFigure = figureManager.findFigureById(figureIds.get(0));
		assertNotNull(RPGFigure);
		figureManager.deleteFigure(RPGFigure);
		assertNull(figureManager.findFigureById(figureIds.get(0)));
		assertEquals(prevSize-1, figureManager.findAllFigures().size());
	}


	@Test
	public void findFigureByNameTest() {
		List<RPGFigure> RPGFigures = figureManager.findFiguresByName("skeleton");
		assertEquals("skeleton", RPGFigures.get(0).getName());
	}
}
