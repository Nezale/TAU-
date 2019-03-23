package pl.tau.BoardGame.Dao;

import pl.tau.BoardGame.Domain.RPGfigure;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class FigureDaoImpl implements FigureDao {
    public PreparedStatement preparedStatementGetAll;
    public PreparedStatement preparedStatementInsert;
    public PreparedStatement preparedStatementGetFigure;
    public PreparedStatement preparedStatementDeleteFigure;

    Connection connection;
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        preparedStatementGetAll = connection.prepareStatement(
                "SELECT id, name, HP FROM RPGfigure ORDER BY id");
        preparedStatementInsert= connection.prepareStatement(
                "INSERT INTO RPGfigure (name, HP) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        preparedStatementGetFigure = connection.prepareStatement(
                "SELECT id, name, HP FROM RPGfigure WHERE id = ?");
        preparedStatementDeleteFigure = connection.prepareStatement(
                "DELETE FROM RPGFIGURE WHERE id = ?"
        );
    }

    @Override
    public List<RPGfigure> getAllFigures() {
        try {
            List<RPGfigure> ret = new LinkedList<>();
            ResultSet result = preparedStatementGetAll.executeQuery();
            while(result.next()) {
                RPGfigure f = new RPGfigure();
                f.setId(result.getLong("id"));
                f.setName(result.getString("name"));
                f.setHP(result.getInt("HP"));
                ret.add(f);
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addFigure(RPGfigure figure) throws SQLException {
        preparedStatementInsert.setString(1, figure.getName());
        preparedStatementInsert.setInt(2, figure.getHP());
        int r = preparedStatementInsert.executeUpdate();
        return r;
    }

    @Override
    public RPGfigure getFigure(long id) throws SQLException {
        try {
            preparedStatementGetFigure.setLong(1, id);
            ResultSet rs = preparedStatementGetFigure.executeQuery();

            if (rs.next()) {
                RPGfigure f = new RPGfigure();
                f.setId(rs.getLong("id"));
                f.setName(rs.getString("name"));
                f.setHP(rs.getInt("HP"));
                return f;
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        throw new SQLException("Figure with id " + id + " does not exist");
    }

    @Override
    public int deleteRPGfigure(long id) throws SQLException {
        try {
            preparedStatementDeleteFigure.setLong(1, id);
            return preparedStatementDeleteFigure.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
    }
}
