package model;

import controller.CourseController;

import java.util.regex.Matcher;

public class ViewAverageCommand extends Command{
    protected String getPattern() {
        return "view average for (?<cId>\\d{1,8}) (?<pId>\\d{8})";
    }

    public String call(String command) {
        Matcher matcher = super.getMatcher(command);
        return CourseController.getInstance().viewAverage(matcher.group("cId"), matcher.group("pId"));
    }
}
