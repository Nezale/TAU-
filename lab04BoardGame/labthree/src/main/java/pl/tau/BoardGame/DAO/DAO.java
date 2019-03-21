package pl.tau.BoardGame.DAO;

import pl.tau.BoardGame.Domain.RPGfigure;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    public Connection getConnection();
    public void setConnection(Connection connection) throws SQLException;
    public List<RPGfigure> getAllFigures();
    public int addFigure(RPGfigure figure);
//    public int deleteFigure(RPGfigure figure) throws SQLException;
//    public int updateFigure(RPGfigure figure) throws SQLException;
    public RPGfigure getFigure(long id) throws SQLException;

}
