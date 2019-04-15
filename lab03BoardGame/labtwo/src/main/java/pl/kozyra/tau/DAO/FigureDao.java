package pl.kozyra.tau.DAO;

import pl.kozyra.tau.Domain.RPGfigure;

import java.sql.*;
import java.util.*;

public class FigureDao implements DAO<RPGfigure> {

    private Connection connection;

    private PreparedStatement addFigureStmt;
    private PreparedStatement getAllFiguresStmt;
    private PreparedStatement deleteFigureStmt;
    private PreparedStatement getFigureStmt;
    private PreparedStatement updateFigureStmt;


    public FigureDao(Connection connection) throws SQLException {
        this.connection = connection;

        setConnection(connection);
    }

    public FigureDao() {

    }

    public void createTables() throws SQLException {
        connection.createStatement()
                .executeUpdate("CREATE TABLE" +
                        "RPGfigure(id bigint GENERATED BY DEFAULT AS IDENTITY, " +
                        "name varchar (30) NOT NULL, " + "hp integer )");
    }

    private boolean isDatabaseReady() {
        try {
            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
            boolean tableExists = false;
            while (rs.next()) {
                if ("RPGfigure".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
                    tableExists = true;
                    break;
                }
            }
            return tableExists;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        addFigureStmt = connection.prepareStatement(
                "INSERT INTO RPGfigure (name, hp) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        deleteFigureStmt = connection.prepareStatement("DELETE FROM RPGfigure where id = ?");
        getAllFiguresStmt = connection.prepareStatement("SELECT id, name, hp FROM RPGfigure ORDER BY id");
        getFigureStmt = connection.prepareStatement("SELECT id, name, hp FROM RPGfigure WHERE id = ?");
        updateFigureStmt = connection.prepareStatement("UPDATE RPGfigure SET name=?,hp=? WHERE id = ?");
    }

    @Override
    public List<RPGfigure> getAllFigures() {
        List<RPGfigure> figures = new LinkedList<>();
        try {
            ResultSet rs = getAllFiguresStmt.executeQuery();

            while (rs.next()) {
                RPGfigure p = new RPGfigure();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setHP(rs.getInt("hp"));
                figures.add(p);
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return figures;
    }

    @Override
    public int addFigure(RPGfigure figure) {
        int count = 0;
        try {
            addFigureStmt.setString(1, figure.getName());
            addFigureStmt.setInt(2, figure.getHP());
            count = addFigureStmt.executeUpdate();
            ResultSet generatedKeys = addFigureStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                figure.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return count;
    }

    @Override
    public int deleteFigure(RPGfigure figure) throws SQLException {
            deleteFigureStmt.setLong(1, figure.getId());
            int count = deleteFigureStmt.executeUpdate();
        if (count <= 0)
            throw new SQLException("Figure not found");
        return count;
    }


    @Override
    public int updateFigure(RPGfigure figure) throws SQLException {

        int count = 0;
        try {
            updateFigureStmt.setString(1, figure.getName());
            updateFigureStmt.setInt(2, figure.getHP());
            if (figure.getId() != null) {
                updateFigureStmt.setLong(3, figure.getId());
            } else {
                updateFigureStmt.setLong(3, -1);
            }
            count = updateFigureStmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        if (count <= 0)
            throw new SQLException("Figure not found");
        return count;

    }

    @Override
    public RPGfigure getFigure(long id) throws SQLException {
        try {
            getFigureStmt.setLong(1, id);
            ResultSet rs = getFigureStmt.executeQuery();

            if (rs.next()) {
                RPGfigure f = new RPGfigure();
                f.setId(rs.getInt("id"));
                f.setName(rs.getString("name"));
                f.setHP(rs.getInt("hp"));
                return f;
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        throw new SQLException("Figure with id " + id + " does not exist");
    }
}
