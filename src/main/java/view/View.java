package view;

import controller.CourseController;
import controller.StudentController;
import controller.TeacherController;
import enums.Commands;

import java.sql.Connection;
import java.util.Scanner;
import java.util.regex.Matcher;

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
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            Matcher matcher;
            if ((matcher = Commands.getMatcher(input, Commands.ADD_STUDENT)) != null)
                addStudent(matcher, connection);
            else if ((matcher = Commands.getMatcher(input, Commands.ADD_COURSE)) != null)
                addCourse(matcher, connection);
            else if ((matcher = Commands.getMatcher(input, Commands.ADD_TEACHER)) != null)
                addTeacher(matcher, connection);
            else if ((matcher = Commands.getMatcher(input, Commands.ACCEPT_TEACHER)) != null)
                acceptTeacher(matcher, connection);
            else if ((matcher = Commands.getMatcher(input, Commands.REGISTER_STUDENT)) != null)
                registerStudent(matcher, connection);
            else if ((matcher = Commands.getMatcher(input, Commands.DELETE_COURSE)) != null)
                deleteCourse(matcher, connection);
            else if ((matcher = Commands.getMatcher(input, Commands.CHANGE_FAVOURITE)) != null)
                changeFavourite(matcher, connection);
            else if ((matcher = Commands.getMatcher(input, Commands.SCORE)) != null)
                score(matcher, connection);
            else if ((matcher = Commands.getMatcher(input, Commands.VIEW_AVERAGE)) != null)
                viewAverage(matcher, connection);
            else if ((matcher = Commands.getMatcher(input, Commands.VIEW_STUDENT_GPA)) != null)
                viewStudentGpa(matcher, connection);
            else if (Commands.getMatcher(input, Commands.VIEW_STUDENT_FAV) != null)
                viewStudentCount(connection);
            else if (input.strip().equalsIgnoreCase("end"))
                break;
            else
                System.out.println("invalid input!");
        }
        System.out.println("bye");
    }

    private void viewStudentCount(Connection connection) {
        System.out.println(CourseController.getInstance().viewStudentCount(connection));
    }

    private void viewStudentGpa(Matcher matcher, Connection connection) {
        System.out.println(StudentController.getInstance().viewStudentGpa(matcher.group("grade"), connection));
    }

    private void viewAverage(Matcher matcher, Connection connection) {
        System.out.println(CourseController.getInstance().viewAverage(matcher.group("cId"), connection));
    }

    private void score(Matcher matcher, Connection connection) {
        System.out.println(StudentController.getInstance().score(matcher.group("sId"),
                matcher.group("cId"), matcher.group("grade"), connection));
    }

    private void changeFavourite(Matcher matcher, Connection connection) {
        System.out.println(CourseController.getInstance().changeFavourite(matcher.group("id"),
                matcher.group("favourite"), connection));
    }

    private void deleteCourse(Matcher matcher, Connection connection) {
        System.out.println(CourseController.getInstance().deleteCourse(matcher.group("sId"),
                matcher.group("cId"), connection));
    }

    private void registerStudent(Matcher matcher, Connection connection) {
        System.out.println(StudentController.getInstance().registerStudent(matcher.group("sId"),
                matcher.group("cId"), connection));
    }

    private void acceptTeacher(Matcher matcher, Connection connection) {
        System.out.println(TeacherController.getInstance().acceptTeacher(matcher.group("pId"),
                matcher.group("cId"), connection));
    }

    private void addTeacher(Matcher matcher, Connection connection) {
        System.out.println(TeacherController.getInstance().addTeacher(matcher.group("id"),
                matcher.group("name"), connection));
    }

    private void addCourse(Matcher matcher, Connection connection) {
        System.out.println(CourseController.getInstance().addCourse(matcher.group("id"),
                matcher.group("name"), Integer.parseInt(matcher.group("capacity")), connection));
    }

    private void addStudent(Matcher matcher, Connection connection) {
        System.out.println(StudentController.getInstance().addStudent(matcher.group("name"), matcher.group("id"),
                matcher.group("favourite"), matcher.group("grade"), connection));
    }
}
