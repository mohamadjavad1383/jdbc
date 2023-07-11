package app;

import model.*;
import view.View;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Command> commands;
    public static void main(String[] args) {
        createCommands();
        try {
            connect();
        } catch (Exception e) {
            System.out.println("couldn't connect to db");
        }
    }

    private static void createCommands() {
        commands = new ArrayList<>();
        commands.add(new AcceptTeacherCommand());
        commands.add(new AddCourseCommand());
        commands.add(new AddStudentCommand());
        commands.add(new ChangeFavouriteCommand());
        commands.add(new DeleteCourseCommand());
        commands.add(new RegisterStudentCommand());
        commands.add(new ScoreCommand());
        commands.add(new ViewAverageCommand());
        commands.add(new ViewFavouriteCommand());
        commands.add(new ViewGpaCommand());
        commands.add(new ExitCommand());
    }

    private static void connect() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(Config.getInstance().getUrl(),
                Config.getInstance().getUsername(), Config.getInstance().getPassword())) {
            System.out.println("connected");
            View.getInstance().run(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
