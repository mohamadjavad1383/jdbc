package app;

import model.*;
import view.View;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        createCommands();
        try {
            connect();
        } catch (Exception e) {
            System.out.println("couldn't connect to db");
        }
    }

    private static void createCommands() {
        List<Command> commands = new ArrayList<>();
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
        View.getInstance().setCommands(commands);
    }

    private static void connect() throws ClassNotFoundException {
        Config config = new Config("org.postgresql.Driver", "postgres",
                "1274335299" ,"jdbc:postgresql://localhost:5432/university");
        Class.forName(config.getClassForName());
        try (Connection connection = DriverManager.getConnection(config.getUrl(),
                config.getUsername(), config.getPassword())) {
            System.out.println("connected");
            View.getInstance().run(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
