package model;

import controller.TeacherController;

import java.util.regex.Matcher;

public class AcceptTeacherCommand extends Command{
    protected String getPattern() {
        return "accept teacher (?<pId>\\d{8}) (?<cId>\\d{1,8})";
    }

    public String call(String command) {
        Matcher matcher = super.getMatcher(command);
        return TeacherController.getInstance().acceptTeacher(matcher.group("pId"),
                matcher.group("cId"));
    }
}
