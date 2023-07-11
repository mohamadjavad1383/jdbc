package model;

import controller.StudentController;

import java.util.regex.Matcher;

public class ViewGpaCommand extends Command{
    protected String getPattern() {
        return "view student gpa (?<grade>\\d{1,2}(\\.\\d{1,2})?)";
    }

    public String call(String command) {
        Matcher matcher = super.getMatcher(command);
        return StudentController.getInstance().viewStudentGpa(matcher.group("grade"));
    }
}
