package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Controller {
    protected Connection connection;

    protected ResultSet getResultSet(String id, Connection connection, String table) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " where " + table +"Number = ?");
        ps.setString(1, id);
        return ps.executeQuery();
    }
}
