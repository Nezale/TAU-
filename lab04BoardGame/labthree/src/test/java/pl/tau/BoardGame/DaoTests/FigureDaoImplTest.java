package pl.tau.BoardGame.DaoTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.tau.BoardGame.Dao.FigureDao;
import pl.tau.BoardGame.Dao.FigureDaoImpl;
import pl.tau.BoardGame.Domain.RPGfigure;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FigureDaoImplTest {
    Logger LOGGER = Logger.getLogger(FigureDaoImplTest.class.getCanonicalName());
    Random random;
    static List<RPGfigure> initialDatabaseState;


    /**
     * Tylko na potrzeby testów! Przygotujmy odpowiedni ResultSet.
     *
     * UWAGA: Moglibyśmy zaimplementować cały ResultSet, ale wtedy musimy przygotować wszystkie metody które są w nim zadeklarowane.
     */
    abstract class AbstractResultSet implements ResultSet {
        int i;

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
    @Mock
    PreparedStatement getStatementMock;
    @Mock
    PreparedStatement deleteStatementMock;
    @Mock
    PreparedStatement updateStatementMock;


    @Before
    public void setup() throws SQLException {
        random = new Random();
        initialDatabaseState = new LinkedList<>();
        for (int i = 0; i < 10;i++) {
            RPGfigure person = new RPGfigure();
            person.setId(i);
            person.setName("golem"+random.nextInt(1000));
            person.setHP(random.nextInt(50)+1950);
            initialDatabaseState.add(person);
        }
        when(connection.prepareStatement("SELECT id, name, HP FROM RPGfigure ORDER BY id")).thenReturn(selectStatementMock);
        when(connection.prepareStatement("INSERT INTO RPGfigure (name, HP) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)).thenReturn(insertStatementMock);
        when(connection.prepareStatement("SELECT id, name, HP FROM RPGfigure WHERE id = ?")).thenReturn(getStatementMock);
        when(connection.prepareStatement("DELETE FROM RPGfigure WHERE id = ?")).thenReturn(deleteStatementMock);
        when(connection.prepareStatement("UPDATE RPGfigure SET name = ?, HP = ? WHERE id = ?")).thenReturn(updateStatementMock);
    }

    @Test
    public void setConnectionCheck() throws SQLException {
        FigureDaoImpl dao = new FigureDaoImpl();
        dao.setConnection(connection);
        Assert.assertNotNull(dao.getConnection());
        assertEquals(dao.getConnection(), connection);
    }

    @Test
    public void setConnectionCreatesQueriesCheck() throws SQLException {
        FigureDaoImpl dao = new FigureDaoImpl();
        dao.setConnection(connection);
        Assert.assertNotNull(dao.preparedStatementGetAll);
        verify(connection).prepareStatement("SELECT id, name, HP FROM RPGfigure ORDER BY id");
    }

    @Test
    public void getAllCheck() throws SQLException {

        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenCallRealMethod();
        when(mockedResultSet.getString("name")).thenCallRealMethod();
        when(mockedResultSet.getInt("HP")).thenCallRealMethod();
        when(selectStatementMock.executeQuery()).thenReturn(mockedResultSet);

        FigureDaoImpl dao = new FigureDaoImpl();
        dao.setConnection(connection);
        List<RPGfigure> retrievedFigures = dao.getAllFigures();
        Assert.assertThat(retrievedFigures, equalTo(initialDatabaseState));

        verify(selectStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(initialDatabaseState.size())).getLong("id");
        verify(mockedResultSet, times(initialDatabaseState.size())).getString("name");
        verify(mockedResultSet, times(initialDatabaseState.size())).getInt("HP");
        verify(mockedResultSet, times(initialDatabaseState.size()+1)).next();
    }

    @Test
    public void addInOrderCheck() throws Exception {

        InOrder inorder = inOrder(insertStatementMock);
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        FigureDaoImpl dao = new FigureDaoImpl();
        dao.setConnection(connection);
        RPGfigure figure = new RPGfigure();
        figure.setName("skeleton");
        figure.setHP(200);
        dao.addFigure(figure);

        inorder.verify(insertStatementMock, times(1)).setString(1, "skeleton");
        inorder.verify(insertStatementMock, times(1)).setInt(2, 200);
        inorder.verify(insertStatementMock).executeUpdate();
    }

    @Test
    public void getRPGfigurecheck() throws Exception {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenReturn(initialDatabaseState.get(2).getId());
        when(mockedResultSet.getString("name")).thenReturn(initialDatabaseState.get(2).getName());
        when(mockedResultSet.getInt("HP")).thenReturn(initialDatabaseState.get(2).getHP());
        when(getStatementMock.executeQuery()).thenReturn(mockedResultSet);

        FigureDaoImpl dao = new FigureDaoImpl();
        dao.setConnection(connection);
        RPGfigure retrievedFigure= dao.getFigure(2);
        Assert.assertThat(retrievedFigure, equalTo(initialDatabaseState.get(2)));


        Mockito.verify(getStatementMock, times(1)).setLong(1, 2);
        Mockito.verify(getStatementMock, times(1)).executeQuery();
        Mockito.verify(mockedResultSet, times(1)).getLong("id");
        Mockito.verify(mockedResultSet, times(1)).getString("name");
        Mockito.verify(mockedResultSet, times(1)).getInt("HP");
        Mockito.verify(mockedResultSet, times(1)).next();

    }

    @Test
    public void updateRPGfigureCheck() throws SQLException {
        InOrder inOrder = inOrder(updateStatementMock);
        when(updateStatementMock.executeUpdate()).thenReturn(1);

        FigureDaoImpl dao = new FigureDaoImpl();
        dao.setConnection(connection);
        RPGfigure figure = initialDatabaseState.get(2);
        figure.setName("skeleton");
        figure.setHP(350);
        dao.updateRPGfigure(figure);

        inOrder.verify(updateStatementMock, times(1)).setString(1, "skeleton");
        inOrder.verify(updateStatementMock, times(1)).setInt(2, 350);
        inOrder.verify(updateStatementMock, times(1)).setLong(3, 2);
        inOrder.verify(updateStatementMock).executeUpdate();
    }

    @Test
    public void deleteRPGfigureCheck() throws Exception {
        InOrder inOrder = inOrder(deleteStatementMock);
        when(deleteStatementMock.executeUpdate()).thenReturn(1);

        FigureDaoImpl dao = new FigureDaoImpl();
        dao.setConnection(connection);
        RPGfigure figure = initialDatabaseState.get(2);
        dao.deleteRPGfigure(figure);

        inOrder.verify(deleteStatementMock, times(1)).setLong(1, 2);
        inOrder.verify(deleteStatementMock).executeUpdate();

    }


}
