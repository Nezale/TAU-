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
@Rollback
@Commit
@Transactional(transactionManager = "txManager")
public class FigureManagerTest {
    @Autowired
    FigureManager figureManager;

    List<Long> figureIds;
    private List<Long> ownerIds;

    @Before
    public void setup() {
        figureIds = new LinkedList<>();
        figureIds.add(figureManager.addFigure(new Figure("skeleton", 500)));
        figureIds.add(figureManager.addFigure(new Figure("ragnaros", 200)));
        figureIds.add(figureManager.addFigure(new Figure("gnar", 300)));

        Figure figure = figureManager.findFigureById(figureIds.get(0));
        Figure figure1 = figureManager.findFigureById(figureIds.get(1));
        ownerIds = new LinkedList<>();
        ownerIds.add(figureManager.addOnwer(new Owner("Adrzej", new LinkedList<Figure>(Arrays.asList(figure)))));
        ownerIds.add(figureManager.addOnwer(new Owner("Konstanty", new LinkedList<Figure>(Arrays.asList(figure1)))));
    }

    @Test
    public void addPhoneTest() {
        assertTrue(figureIds.size() > 0);
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
    public void deleteFigureTest() {
        int prevSize = figureManager.findAllFigure().size();
        Figure figure = figureManager.findFigureById(figureIds.get(0));
        assertNotNull(figure);
        figureManager.deleteFigure(figure);
        assertNull(figureManager.findFigureById(figureIds.get(0)));
        assertEquals(prevSize - 1, figureManager.findAllFigure().size());
    }

    @Test()
    public void updateFigureTest() {
        Figure f = figureManager.findFigureById(1L);
        f.setName("rumcajs");
        figureManager.updateFigure(f);
        Assert.assertEquals("rumcajs", figureManager.findFigureById(1L).getName());
    }

    @Test
    public void findfigureByNameTest() {
        List<Figure> figures = figureManager.findFigureByName("rag");
        assertEquals("ragnaros", figures.get(0).getName());
    }



}