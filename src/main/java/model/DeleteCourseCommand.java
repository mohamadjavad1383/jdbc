package model;

import controller.CourseController;

import java.util.regex.Matcher;

public class DeleteCourseCommand extends Command{
    protected String getPattern() {
        return "delete course (?<sId>\\d{8}) (?<cId>\\d{1,8}) (?<pId>\\d{8})";
    }

    public String call(String command) {
        Matcher matcher = super.getMatcher(command);
        return CourseController.getInstance().deleteCourse(matcher.group("sId"),
                matcher.group("cId"), matcher.group("pId"));
    }
}
