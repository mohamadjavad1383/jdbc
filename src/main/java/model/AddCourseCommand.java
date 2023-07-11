package model;

import controller.CourseController;

import java.util.regex.Matcher;

public class AddCourseCommand extends Command{
    protected String getPattern() {
        return "add course (?<id>\\d{1,8}) (?<name>\\S+) (?<capacity>\\d+)";
    }

    public String call(String command) {
        Matcher matcher = super.getMatcher(command);
        return CourseController.getInstance().addCourse(matcher.group("id"),
                matcher.group("name"), Integer.parseInt(matcher.group("capacity")));
    }
}
