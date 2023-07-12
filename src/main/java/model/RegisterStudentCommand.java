package model;

import controller.StudentController;

import java.util.regex.Matcher;

public class RegisterStudentCommand extends Command{
    protected String getPattern() {
        return "register student (?<sId>\\d{8}) (?<cId>\\d{1,8}) (?<pId>\\d{8})";
    }

    public String call(String command) {
        Matcher matcher = super.getMatcher(command);
        return StudentController.getInstance().registerStudent(matcher.group("sId"),
                matcher.group("cId"), matcher.group("pId"));
    }
}
