package pl.kozyra.tau.DAO;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import pl.kozyra.tau.Domain.RPGfigure;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(BlockJUnit4ClassRunner.class)
public class FigureDaoTest {

    FigureDao dao;
    FigureDao daoTest;

    @Before
    public void initData(){
        RPGfigure f1 = new RPGfigure();
        RPGfigure f2 = new RPGfigure();

        f1.setId(1L);
        f1.setName("Runic Golem");
        f1.setHP(150);

        f2.setId(2L);
        f2.setName("skeleton");
        f2.setHP(100);

        dao = new FigureDao();
        daoTest = new FigureDao();

        dao.figures = new HashMap<>();
        daoTest.figures = new HashMap<>();

        dao.figures.put(1L,f1);
        dao.figures.put(2L,f2);

        daoTest.figures.put(1L,f1);
        daoTest.figures.put(2L,f2);
    }

    @Test
    public void createDaoCheck(){
        assertNotNull(dao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void redDaoExceptionCheck(){
        dao.get(20L);
    }

    @Test
    public void readDaoCheck(){
        Optional <RPGfigure> figure = dao.get(1L);
        assertThat(figure.get().getName(), is("Runic Golem"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void updateDaoExceptionTest(){
        RPGfigure figure = new RPGfigure();
        figure.setId(12L);
        figure.setName("whatever");
        figure.setHP(1203);

        dao.update(figure);
    }

    @Test
    public void updateDaoCheck(){
        RPGfigure f1 = new RPGfigure();
        f1.setId(1L);
        f1.setName("whatever");
        f1.setHP(1203);

        daoTest.figures.put(1L,f1);

        System.out.println(dao.figures.values().toString());

        /*Map<Long,RPGfigure> testMap = new HashMap<Long, RPGfigure>(dao.figures);*/


        Collection<RPGfigure> expected = daoTest.figures.values();

       /* for (RPGfigure f : expected) {
            if (f.getId() == 1) {
                f.setName("whatever");
                f.setHP(1203);
            }
        }*/

            System.out.println(expected.toString());

        dao.update(f1);

        Collection<RPGfigure> real = dao.figures.values();

        System.out.println(real.toString());

        assertArrayEquals(expected.toArray(), real.toArray());

    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteDaoExceptionCheck(){
        dao.delete(5L);
    }

    @Test
    public void deleteDaoCheck(){
        RPGfigure f1 = new RPGfigure();
        f1.setId(1L);
        f1.setName("Runic Golem");
        f1.setHP(150);

        daoTest.figures.remove(1L);
        Collection<RPGfigure> expected = daoTest.figures.values();

        dao.delete(1L);

        Collection<RPGfigure> real = dao.figures.values();

        assertArrayEquals(expected.toArray(),real.toArray());
    }

    @Test
    public void readAllCheck(){
        Collection<RPGfigure> test = dao.figures.values();
        assertArrayEquals(test.toArray(), dao.getAll().toArray());
    }

}
