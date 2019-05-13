package pl.tau.boardgame.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.tau.boardgame.domain.Figure;
import pl.tau.boardgame.domain.Owner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/beans.xml"})
//@Rollback
@Commit
@Transactional(transactionManager = "txManager")
public class FigureManagerTest {
    @Autowired
    FigureManager libraryManager;

    List<Long> figureIds;
    private List<Long> ownerIds;

    @Before
    public void setup() {
        figureIds = new LinkedList<>();
        figureIds.add(libraryManager.addFigure(new Figure("skeleton", 500)));
        figureIds.add(libraryManager.addFigure(new Figure("ragnaros", 200)));
        figureIds.add(libraryManager.addFigure(new Figure("gnar", 300)));

        Figure figure = libraryManager.findFigureById(figureIds.get(0));
        Figure figure1 = libraryManager.findFigureById(figureIds.get(1));
        ownerIds = new LinkedList<>();
        ownerIds.add(libraryManager.addOnwer(new Owner("Adrzej", new LinkedList<Figure>(Arrays.asList(figure)))));
        ownerIds.add(libraryManager.addOnwer(new Owner("Konstanty", new LinkedList<Figure>(Arrays.asList(figure1)))));
    }

    @Test
    public void addPhoneTest() {
        assertTrue(figureIds.size() > 0);
    }

}