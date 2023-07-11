package model;

import controller.StudentController;

import java.util.regex.Matcher;

public class ScoreCommand extends Command{
    protected String getPattern() {
        return "score (?<sId>\\d{8}) (?<cId>\\d{1,8}) (?<grade>\\d{1,2}\\.?\\d{1,2})";
    }

    public String call(String command) {
        Matcher matcher = super.getMatcher(command);
        return StudentController.getInstance().score(matcher.group("sId"),
                matcher.group("cId"), matcher.group("grade"));
    }
}
