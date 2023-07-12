package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Controller {
    protected Connection connection;

    protected ResultSet getResultSet(String id, Connection connection, String table) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + table + " where " + table +"Number = ?");
        preparedStatement.setString(1, id);
        return preparedStatement.executeQuery();
    }
}
