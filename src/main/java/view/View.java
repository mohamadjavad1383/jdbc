package view;

import app.Main;
import controller.CourseController;
import controller.StudentController;
import controller.TeacherController;

import java.sql.Connection;
import java.util.Scanner;

public class View {
    private static View instance;

    private View() {
    }

    public static View getInstance() {
        if (instance == null)
            instance = new View();
        return instance;
    }

    public void run(Connection connection) {
        StudentController.getInstance().setConnection(connection);
        TeacherController.getInstance().setConnection(connection);
        CourseController.getInstance().setConnection(connection);
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            try {
                System.out.println(Main.commands.stream().filter(c -> c.getMatcher(input) != null)
                         .findFirst().get().call(input));
            } catch (Exception e) {
                System.out.println("invalid command");
            }
        }
    }
}
