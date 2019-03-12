package pl.kozyra.tau.DAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.JUnit4;
import pl.kozyra.tau.Domain.RPGfigure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RunWith(JUnit4.class)
public class FigureDaoTest {

    private static final Logger LOGGER = Logger.getLogger(FigureDaoTest.class.getCanonicalName());

    @Rule
    public Timeout globalTimeout = new Timeout(1000);

    public static String url = "jdbc:hsqldb:hsql://localhost/workdb";

    FigureDao figureManager;
    List<RPGfigure> expectedDbState;

    @Before
    public void setup() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        try {
            connection.createStatement()
                    .executeUpdate("CREATE TABLE " +
                            "RPGfigure(id bigint GENERATED BY DEFAULT AS IDENTITY, "
                            + "name varchar(30) NOT NULL, " + "hp integer)");

        } catch (SQLException e) {}

        Random rand = new Random();
        PreparedStatement addFigureStmt = connection.prepareStatement(
                "INSERT INTO RPGfigure (name, hp) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);

        expectedDbState = new LinkedList<RPGfigure>();
        for (int i = 0; i < 10; i++) {
            RPGfigure figure = new RPGfigure(rand.nextLong(), "Golem" + rand.nextInt(1000), 1000 + rand.nextInt(1000));
            try {
                addFigureStmt.setString(1, figure.getName());
                addFigureStmt.setInt(2, figure.getHP());
                addFigureStmt.executeUpdate();
                ResultSet generatedKeys = addFigureStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    figure.setId(generatedKeys.getLong(1));
                }
            } catch (SQLException e) {
                throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
            }

            expectedDbState.add(figure);
        }
        figureManager = new FigureDao(connection);
    }

    @After
    public void cleanup() throws SQLException{
        Connection connection = DriverManager.getConnection(url);
        try {
            connection.prepareStatement("DELETE FROM RPGfigure").executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.FINEST,"Probably the database was not yet initialized");
        }
    }

    @Test
    public void AddCheck(){
        RPGfigure figure = new RPGfigure();
        figure.setName("Runic golem");
        figure.setHP(1200);

        assertEquals(1,figureManager.addFigure(figure));

        expectedDbState.add(figure);
        assertThat(figureManager.getAllFigures(), equalTo(expectedDbState));
    }

    @Test
    public void GetCheck() throws Exception {
        RPGfigure figure = expectedDbState.get(7);
        assertEquals(figure, figureManager.getFigure(figure.getId()));
    }

    @Test(expected = SQLException.class)
    public void DeleteFailCheck() throws SQLException {
        RPGfigure f = expectedDbState.get(3);
        expectedDbState.remove(f);
        assertEquals(1, figureManager.deleteFigure(f));
        assertThat(figureManager.getAllFigures(), equalTo(expectedDbState));
        assertNull(figureManager.getFigure(f.getId()));
    }

    @Test()
    public void deleteSuccessCheck() throws SQLException {
        RPGfigure f = expectedDbState.get(3);
        expectedDbState.remove(f);
        assertEquals(1, figureManager.deleteFigure(f));
        assertThat(figureManager.getAllFigures(), equalTo(expectedDbState));
    }

    @Test()
    public void checkUpdatingSuccess() throws SQLException {
        RPGfigure f = expectedDbState.get(3);
        f.setName("Runic Golem");
        expectedDbState.set(3, f);
        assertEquals(1, figureManager.updateFigure(f));
        assertThat(figureManager.getAllFigures(), equalTo(expectedDbState));
    }

    @Test(expected = SQLException.class)
    public void checkUpdatingFailure() throws SQLException {
        RPGfigure f = new RPGfigure(505050L,"Runic golem",123);
        assertEquals(1, figureManager.updateFigure(f));
    }

}
