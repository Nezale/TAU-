package pl.kozyra.tau.DAO;

import pl.kozyra.tau.Domain.RPGfigure;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    public Connection getConnection();
    public void setConnection(Connection connection) throws SQLException;
    public List<RPGfigure> getAllFigures();
    public int addFigure(RPGfigure figure);
    public int deleteFigure(RPGfigure figure);
    public int updateFigure(RPGfigure figure) throws SQLException;
    public RPGfigure getFigure(long id) throws SQLException;

}
