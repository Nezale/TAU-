package pl.kozyra.tau.Domain;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class FigureTest {

    @Test
    public void createFigureCheck(){
        RPGfigure figure = new RPGfigure();
        assertNotNull(figure);
    }

    @Test
    public void figureGettersAndSettersCheck(){
        RPGfigure figure = new RPGfigure();
        figure.setId(1);
        figure.setName("test");
        figure.setHP(100);
        assertEquals(1,figure.getId().intValue());
        assertEquals("test",figure.getName());
        assertEquals(100,figure.getHP());

    }

    @Test
    public void figureToStringCheck(){
        RPGfigure figure = new RPGfigure();
        figure.setId(1);
        figure.setName("test");
        figure.setHP(100);
        assertThat("FigureDao{id=1, name=test, HP=100}", is(figure.toString()));
    }
}
