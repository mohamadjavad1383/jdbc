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
        StudentController.getInstance().setConnection(connection);
        TeacherController.getInstance().setConnection(connection);
        CourseController.getInstance().setConnection(connection);
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            Matcher matcher;
            if ((matcher = Commands.getMatcher(input, Commands.ADD_STUDENT)) != null)
                addStudent(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.ADD_COURSE)) != null)
                addCourse(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.ADD_TEACHER)) != null)
                addTeacher(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.ACCEPT_TEACHER)) != null)
                acceptTeacher(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.REGISTER_STUDENT)) != null)
                registerStudent(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.DELETE_COURSE)) != null)
                deleteCourse(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.CHANGE_FAVOURITE)) != null)
                changeFavourite(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.SCORE)) != null)
                score(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.VIEW_AVERAGE)) != null)
                viewAverage(matcher);
            else if ((matcher = Commands.getMatcher(input, Commands.VIEW_STUDENT_GPA)) != null)
                viewStudentGpa(matcher);
            else if (Commands.getMatcher(input, Commands.VIEW_STUDENT_FAV) != null)
                viewStudentCount();
            else if (input.strip().equalsIgnoreCase("end"))
                break;
            else
                System.out.println("invalid input!");
        }
        System.out.println("bye");
    }

    private void viewStudentCount() {
        System.out.println(CourseController.getInstance().viewStudentCount());
    }

    private void viewStudentGpa(Matcher matcher) {
        System.out.println(StudentController.getInstance().viewStudentGpa(matcher.group("grade")));
    }

    private void viewAverage(Matcher matcher) {
        System.out.println(CourseController.getInstance().viewAverage(matcher.group("cId"), matcher.group("pId")));
    }

    private void score(Matcher matcher) {
        System.out.println(StudentController.getInstance().score(matcher.group("sId"),
                matcher.group("cId"), matcher.group("grade")));
    }

    private void changeFavourite(Matcher matcher) {
        System.out.println(CourseController.getInstance().changeFavourite(matcher.group("id"),
                matcher.group("favourite")));
    }

    private void deleteCourse(Matcher matcher) {
        System.out.println(CourseController.getInstance().deleteCourse(matcher.group("sId"),
                matcher.group("cId"), matcher.group("pId")));
    }

    private void registerStudent(Matcher matcher) {
        System.out.println(StudentController.getInstance().registerStudent(matcher.group("sId"),
                matcher.group("cId"), matcher.group("pId")));
    }

    private void acceptTeacher(Matcher matcher) {
        System.out.println(TeacherController.getInstance().acceptTeacher(matcher.group("pId"),
                matcher.group("cId")));
    }

    private void addTeacher(Matcher matcher) {
        System.out.println(TeacherController.getInstance().addTeacher(matcher.group("id"),
                matcher.group("name")));
    }

    private void addCourse(Matcher matcher) {
        System.out.println(CourseController.getInstance().addCourse(matcher.group("id"),
                matcher.group("name"), Integer.parseInt(matcher.group("capacity"))));
    }

    private void addStudent(Matcher matcher) {
        System.out.println(StudentController.getInstance().addStudent(matcher.group("name"), matcher.group("id"),
                matcher.group("favourite"), matcher.group("grade")));
    }
}
