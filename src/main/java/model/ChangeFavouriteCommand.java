package model;

import controller.CourseController;

import java.util.regex.Matcher;

public class ChangeFavouriteCommand extends Command{
    protected String getPattern() {
        return "change (?<id>\\d{8}) fave (?<favourite>\\d{1,8})";
    }

    public String call(String command) {
        Matcher matcher = super.getMatcher(command);
        return CourseController.getInstance().changeFavourite(matcher.group("id"),
                matcher.group("favourite"));
    }
}
