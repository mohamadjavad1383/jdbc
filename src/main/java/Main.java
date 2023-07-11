import view.View;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            connect();
        } catch (Exception e) {
            System.out.println("couldn't connect to db");
        }
    }

    private static void connect() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university",
                "postgres", "1274335299")) {
            System.out.println("connected");
            View.getInstance().run(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
