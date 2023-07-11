package enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    ADD_STUDENT("add student (?<id>\\d{8}) (?<name>\\S+) (?<grade>\\d{1,2}(\\.\\d{1,2})?)( (?<favourite>\\d{1,8}))?"),
    ADD_COURSE("add course (?<id>\\d{1,8}) (?<name>\\S+) (?<capacity>\\d+)"),
    ADD_TEACHER("add teacher (?<id>\\d{8}) (?<name>\\S+)"),
    ACCEPT_TEACHER("accept teacher (?<pId>\\d{8}) (?<cId>\\d{1,8})"),
    REGISTER_STUDENT("register student (?<sId>\\d{8}) (?<cId>\\d{1,8}) (?<pId>\\d{8})"),
    DELETE_COURSE("delete course (?<sId>\\d{8}) (?<cId>\\d{1,8})"),
    CHANGE_FAVOURITE("change (?<id>\\d{8}) fave (?<favourite>\\d{1,8})"),
    SCORE("score (?<sId>\\d{8}) (?<cId>\\d{1,8}) (?<grade>\\d{1,2}\\.?\\d{1,2})"),
    VIEW_AVERAGE("view average for (?<cId>\\d{1,8})"),
    VIEW_STUDENT_GPA("view student gpa (?<grade>\\d{1,2}(\\.\\d{1,2})?)"),
    VIEW_STUDENT_FAV("view student count for each favourite course");

    private final String regex;

    Commands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, Commands regex) {
        Matcher matcher = Pattern.compile(regex.regex).matcher(command);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
