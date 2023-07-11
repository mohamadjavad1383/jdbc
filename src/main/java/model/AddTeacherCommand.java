package model;

import controller.TeacherController;

import java.util.regex.Matcher;

public class AddTeacherCommand extends Command{
    protected String getPattern() {
        return "add teacher (?<id>\\d{8}) (?<name>\\S+)";
    }

    public String call(String command) {
        Matcher matcher = super.getMatcher(command);
        return TeacherController.getInstance().addTeacher(matcher.group("id"),
                matcher.group("name"));
    }
}
