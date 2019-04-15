package pl.tau.BoardGame.Dao;

import pl.tau.BoardGame.Domain.RPGfigure;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface FigureDao {
    public Connection getConnection();
    public void setConnection(Connection connection) throws SQLException;
    public List<RPGfigure> getAllFigures();

    public int addFigure(RPGfigure figure) throws SQLException;
    public int deleteRPGfigure(RPGfigure figure) throws SQLException;
     public int updateRPGfigure(RPGfigure figure) throws SQLException;
    public RPGfigure getFigure(long id) throws SQLException;
}
