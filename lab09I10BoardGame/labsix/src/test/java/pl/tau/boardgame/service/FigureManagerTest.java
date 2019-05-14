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
import org.springframework.test.annotation.Rollback;
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
    FigureManager figureManager;

    private List<Long> figureIds;
    private List<Long> ownerIds;

    @Before
    public void setup() {
        figureIds = new LinkedList<>();
        figureIds.add(figureManager.addFigure(new Figure("skeleton", 500)));
        figureIds.add(figureManager.addFigure(new Figure("ragnaros", 200)));
        figureIds.add(figureManager.addFigure(new Figure("gnar", 300)));

        Figure figure = figureManager.findFigureById(figureIds.get(0));
        Figure figure1 = figureManager.findFigureById(figureIds.get(1));
        Figure figure2 = figureManager.findFigureById(figureIds.get(2));
        ownerIds = new LinkedList<>();
        ownerIds.add(figureManager.addOwner(new Owner("Andrzej", new LinkedList<Figure>(Arrays.asList(figure)))));
        ownerIds.add(figureManager.addOwner(new Owner("Konstanty", new LinkedList<Figure>(Arrays.asList(figure1,figure2)))));
    }

    @Test
    public void addFigureTest() {
        assertTrue(figureIds.size() > 0);
    }

    @Test
    public void addOwnerTest() {
        assertTrue(ownerIds.size() > 0);
    }

    @Test
    public void getAllFiguresTest() {
        List<Long> foundIds = new LinkedList<>();
        for (Figure figure : figureManager.findAllFigure()) {
            if (figureIds.contains(figure.getId())) foundIds.add(figure.getId());
        }
        assertEquals(figureIds.size(), foundIds.size());
    }

    @Test
    public void getFigureByIdTest() {
        assertEquals("skeleton", figureManager.findFigureById(figureIds.get(0)).getName());
    }

    @Test
    public void deleteFigureTest() {
        int prevSize = figureManager.findAllFigure().size();
        Figure figure = figureManager.findFigureById(figureIds.get(0));
        assertNotNull(figure);
        figureManager.deleteFigure(figure);
        assertNull(figureManager.findFigureById(figureIds.get(0)));
        assertEquals(prevSize - 1, figureManager.findAllFigure().size());
    }

    @Test
    public void findOwnerByIdTest() {
        assertEquals("Andrzej", figureManager.findOwnerById(ownerIds.get(0)).getFirstName());
    }

    @Test()
    public void updateFigureTest() {
        Figure f = figureManager.findFigureById(figureIds.get(1));
        f.setName("rumcajs");
        figureManager.updateFigure(f);
        Assert.assertEquals("rumcajs", figureManager.findFigureById(figureIds.get(1)).getName());
    }

    @Test
    public void findfigureByNameTest() {
        List<Figure> figures = figureManager.findFigureByName("rag");
        assertEquals("ragnaros", figures.get(0).getName());
    }

    @Test
    public void findFiguresByOwner() {
        Owner owner = figureManager.findOwnerById(ownerIds.get(1));
        List<Figure> figures = figureManager.getAllFiguresForOwner(owner);
        assertEquals(2, figures.size());
    }

    @Test
    public void transferFigureToAnotherOwner() {
        Figure figure = figureManager.findFigureById(figureIds.get(0));
        Figure figure1 = figureManager.findFigureById(figureIds.get(1));
        Owner owner = figureManager.findOwnerById(ownerIds.get(0));
        Owner owner1 = figureManager.findOwnerById(ownerIds.get(1));
        figureManager.transferFigureToAnotherOwner(
                figure, figure1, owner, owner1);
        assertEquals("ragnaros", figureManager.getAllFiguresForOwner(owner).get(1).getName());
        assertEquals(1, figureManager.getAllFiguresForOwner(owner1).size());

    }


}