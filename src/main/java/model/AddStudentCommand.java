package model;

import controller.StudentController;

import java.util.regex.Matcher;

public class AddStudentCommand extends Command{
    protected String getPattern() {
        return "add student (?<id>\\d{8}) (?<name>\\S+) (?<grade>\\d{1,2}(\\.\\d{1,2})?)( (?<favourite>\\d{1,8}))?";
    }

    public String call(String command) {
        Matcher matcher = super.getMatcher(command);
        return StudentController.getInstance().addStudent(matcher.group("name"), matcher.group("id"),
                matcher.group("favourite"), matcher.group("grade"));
    }
}
