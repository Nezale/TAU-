package pl.kozyra.boardGame.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.kozyra.boardGame.domain.RPGFigure;
import pl.kozyra.boardGame.domain.Owner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/beans.xml"})
//@Rollback
@Commit
@Transactional(transactionManager = "txManager")
public class RPGFigureManagerTest {

	@Autowired
	RPGFigureManager libraryManager;

	private List<Long> RPGFigureIds;
	private List<Long> OwnersIds;

  /*  private RPGFigure addRPGFigureHelper(String Name, Integer year) {
        Long RPGFigureId;
        RPGFigure RPGFigure;
        RPGFigure = new RPGFigure();
        RPGFigure.setName(Name);
        RPGFigure.setYear(year);
        RPGFigure.setId(null);
        RPGFigureIds.add(RPGFigureId = libraryManager.addRPGFigurejkk(RPGFigure));
        assertNotNull(RPGFigureId);
        return RPGFigure;
    }*/


	@Before
	public void setup() {
		RPGFigureIds = new LinkedList<>();
		RPGFigureIds.add(libraryManager.addRPGFigurejkk(new RPGFigure("Opowiadanie", 2003)));
		RPGFigureIds.add(libraryManager.addRPGFigurejkk(new RPGFigure("Wiedzmin", 2005)));
		RPGFigure RPGFigure = libraryManager.findRPGFigureById(RPGFigureIds.get(0));
		RPGFigure RPGFigure1 = libraryManager.findRPGFigureById(RPGFigureIds.get(1));
		OwnersIds = new LinkedList<>();
		OwnersIds.add(libraryManager.addOwner(new Owner("Andrzej", new LinkedList<RPGFigure>(Arrays.asList(RPGFigure)))));
		OwnersIds.add(libraryManager.addOwner(new Owner("Jan", new LinkedList<RPGFigure>(Arrays.asList(RPGFigure1)))));
	}

	@Test
	public void addRPGFigureTest() {
		assertTrue(RPGFigureIds.size() > 0);
	}

	@Test
	public void addOwnerTest() {
		assertTrue(OwnersIds.size() > 0);
	}

	@Test
	public void getAllRPGFiguresTest() {
		List<Long> foundIds = new LinkedList<>();
		for (RPGFigure RPGFigure : libraryManager.findAllRPGFigure()) {
			if (RPGFigureIds.contains(RPGFigure.getId())) foundIds.add(RPGFigure.getId());
		}
		assertEquals(RPGFigureIds.size(), foundIds.size());
	}

	@Test
	public void getRPGFigureByIdTest() {
		RPGFigure RPGFigure = libraryManager.findRPGFigureById(RPGFigureIds.get(0));
		assertEquals("Opowiadanie", RPGFigure.getName());
	}

	@Test
	public void deleteRPGFigureTest() {
		RPGFigure RPGFigure = libraryManager.findRPGFigureById(RPGFigureIds.get(0));
		libraryManager.deleteRPGFigure(RPGFigure);
		assertNull(libraryManager.findRPGFigureById(RPGFigure.getId()));

	}
	@Test()
	public void updateRPGFigureTest() {
		RPGFigure b = libraryManager.findRPGFigureById(1L);
		b.setName("Fraszka");
		libraryManager.updateRPGFigure(b);
		assertEquals("Fraszka",libraryManager.findRPGFigureById(1L).getName());
	}

	@Test
	public void findRPGFiguresByNameTest() {
		List<RPGFigure> RPGFigures = libraryManager.findRPGFigureByName("Opo");
		assertEquals("Opowiadanie", RPGFigures.get(0).getName());
	}

	@Test
	public void findRPGFiguresByOwner() {
		Owner owner = libraryManager.findOwnerById(OwnersIds.get(1));
		List<RPGFigure> RPGFigures = libraryManager.getAllRPGFiguresForOwner(owner);
		assertEquals(1, RPGFigures.size());
	}

	@Test
	public void findOwnerByIdTest() {
		assertEquals("Andrzej", libraryManager.findOwnerById(OwnersIds.get(0)).getFirstName());
	}

	@Test
	public void transferRPGFigureToAnotherOwner() {
		RPGFigure RPGFigure = libraryManager.findRPGFigureById(RPGFigureIds.get(0));
		RPGFigure RPGFigure1 = libraryManager.findRPGFigureById(RPGFigureIds.get(1));
		Owner owner = libraryManager.findOwnerById(OwnersIds.get(0));
		Owner owner1 = libraryManager.findOwnerById(OwnersIds.get(1));
		libraryManager.transferRPGFigureToAnotherOwner(
				RPGFigure, RPGFigure1, owner, owner1);
		assertEquals("Wiedzmin",libraryManager.getAllRPGFiguresForOwner(owner).get(0).getName());
		assertEquals(1,libraryManager.getAllRPGFiguresForOwner(owner1).size());

	}

}
