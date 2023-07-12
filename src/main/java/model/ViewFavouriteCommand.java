package model;

import controller.CourseController;

public class ViewFavouriteCommand extends Command{
    protected String getPattern() {
        return "view student count for each favourite course";
    }

    public String call(String command) {
        return CourseController.getInstance().viewStudentCount();
    }
}
