package pl.tau.BoardGame.DAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.JUnit4;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.tau.BoardGame.Domain.RPGfigure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RunWith(MockitoJUnitRunner.class)
public class FigureDaoTest {

    private static final Logger LOGGER = Logger.getLogger(FigureDaoTest.class.getCanonicalName());

    private Random random;
    private List<RPGfigure> initialDatabaseState;

    abstract class AbstractResultSet implements ResultSet {
        int i = 1;

        @Override
        public int getInt(String s) throws SQLException {
            return initialDatabaseState.get(i-1).getHP();
        }
        @Override
        public long getLong(String s) throws SQLException {
            return initialDatabaseState.get(i-1).getId();
        }
        @Override
        public String getString(String columnLabel) throws SQLException {
            return initialDatabaseState.get(i-1).getName();
        }
        @Override
        public boolean next() throws SQLException {
            i++;
            if (i > initialDatabaseState.size())
                return false;
            else
                return true;
        }
    }

    @Mock
    Connection connection;
    @Mock
    PreparedStatement selectStatementMock;
    @Mock
    PreparedStatement insertStatementMock;

    @Before
    public void setup() throws SQLException {
        random = new Random();
        initialDatabaseState = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            RPGfigure figure = new RPGfigure();
            figure.setId(i);
            figure.setName("figure" + random.nextInt(1000));
            figure.setHP(random.nextInt(50) + 1950);
            initialDatabaseState.add(figure);
        }
        Mockito.when(connection.prepareStatement("SELECT id, name, hp FROM RPGfigure ORDER BY id")).thenReturn(selectStatementMock);
        Mockito.when(connection.prepareStatement("INSERT INTO RPGfigure (name, HP) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)).thenReturn(insertStatementMock);
    }
        @Test
        public void setConnectionCheck() throws SQLException {
            FigureDao dao = new FigureDao();
            dao.setConnection(connection);
            assertNotNull(dao.getConnection());
            assertEquals(dao.getConnection(), connection);
        }

        @Test
        public void setConnectionCreatesQueriesCheck() throws SQLException {
            FigureDao dao = new FigureDao();
            dao.setConnection(connection);
            assertNotNull(dao.getGetAllFiguresStmt());
            Mockito.verify(connection).prepareStatement("SELECT id, name, hp FROM RPGfigure ORDER BY id");
        }

//        @Test
//        public void addCheck () throws Exception {
//            InOrder inorder = inOrder(insertStatementMock);
//            when(insertStatementMock.executeUpdate()).thenReturn(1);
//
//            FigureDao dao = new FigureDao();
//            dao.setConnection(connection);
//
//            RPGfigure figure = new RPGfigure();
//            figure.setName("Runic golem");
//            figure.setHP(1200);
//
//            dao.addFigure(figure);
//
//            inorder.verify(insertStatementMock, times(1)).setString(1,"Runic golem");
//            inorder.verify(insertStatementMock, times(1)).setInt(2,1200);
//            inorder.verify(insertStatementMock).executeUpdate();
//        }

    @Test
    public void getAllCheck() throws Exception{
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenCallRealMethod();
        when(mockedResultSet.getString("name")).thenCallRealMethod();
        when(mockedResultSet.getInt("hp")).thenCallRealMethod();
        when(selectStatementMock.executeQuery()).thenReturn(mockedResultSet);

        // robimy test

        FigureDao dao = new FigureDao();
        dao.setConnection(connection);
        List<RPGfigure> retrievedFigures = dao.getAllFigures();
        assertThat(retrievedFigures, equalTo(initialDatabaseState));

        // weryfikujeymy wykonanie mockow

        verify(selectStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(initialDatabaseState.size())).getLong("id");
        verify(mockedResultSet, times(initialDatabaseState.size())).getString("name");
        verify(mockedResultSet, times(initialDatabaseState.size())).getInt("hp");
        verify(mockedResultSet, times(initialDatabaseState.size()+1)).next();
    }

//    @Test
//    public void GetCheck() throws Exception {
//        RPGfigure figure = expectedDbState.get(7);
//        assertEquals(figure, figureManager.getFigure(figure.getId()));
//    }
//
//    @Test(expected = SQLException.class)
//    public void getFailCheck() throws SQLException {
//        RPGfigure figure = expectedDbState.get(8);
//        assertEquals(figure,figureManager.getFigure(8L));
//    }
//
//    @Test(expected = SQLException.class)
//    public void DeleteFailCheck() throws SQLException {
//        RPGfigure f = expectedDbState.get(3);
//        expectedDbState.remove(f);
//        assertEquals(1, figureManager.deleteFigure(f));
//        assertThat(figureManager.getAllFigures(), equalTo(expectedDbState));
//        assertNull(figureManager.getFigure(f.getId()));
//    }
//
//    @Test()
//    public void deleteSuccessCheck() throws SQLException {
//        RPGfigure f = expectedDbState.get(3);
//        expectedDbState.remove(f);
//        assertEquals(1, figureManager.deleteFigure(f));
//        assertThat(figureManager.getAllFigures(), equalTo(expectedDbState));
//    }
//
//    @Test()
//    public void checkUpdatingSuccess() throws SQLException {
//        RPGfigure f = expectedDbState.get(3);
//        f.setName("Runic Golem");
//        expectedDbState.set(3, f);
//        assertEquals(1, figureManager.updateFigure(f));
//        assertThat(figureManager.getAllFigures(), equalTo(expectedDbState));
//    }
//
//    @Test(expected = SQLException.class)
//    public void checkUpdatingFailure() throws SQLException {
//        RPGfigure f = new RPGfigure(505050L,"Runic golem",123);
//        assertEquals(1, figureManager.updateFigure(f));
//    }

}
