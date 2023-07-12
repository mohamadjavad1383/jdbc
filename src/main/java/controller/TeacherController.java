package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherController extends Controller{
    private static TeacherController instance;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private TeacherController() {
    }

    public static TeacherController getInstance() {
        if (instance == null)
            instance = new TeacherController();
        return instance;
    }


    public String addTeacher(String id, String name) {
        try {
            ResultSet teacherResult = getResultSet(id, connection, "teacher");
            if (teacherResult.next()) return "id " + id + " already exist";
            PreparedStatement teacherQuery = connection.prepareStatement("INSERT INTO teacher VALUES(?, ?)");
            teacherQuery.setString(1, id);
            teacherQuery.setString(2, name);

            try {
                teacherQuery.executeUpdate();
            } catch (Exception e) {
                return "couldn't add to db";
            }
            return "teacher added successfully";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "something bad happened";
    }

    public String acceptTeacher(String pId, String cId) {
        try {
            ResultSet teacherResult = getResultSet(pId, connection, "teacher");
            ResultSet courseResult = getResultSet(cId, connection, "course");
            if (!teacherResult.next()) return "teacher id " + pId + " does not exist";
            if (!courseResult.next()) return "course id " + cId + " does not exist";
            PreparedStatement teacherCourseQuery = connection.prepareStatement("INSERT INTO teachercourse VALUES(?, ?, ?)");
            teacherCourseQuery.setString(1, pId);
            teacherCourseQuery.setString(2, cId);
            teacherCourseQuery.setString(3, pId + "-" + cId);

            try {
                teacherCourseQuery.executeUpdate();
            } catch (Exception e) {
                return "couldn't add to db";
            }
            return "teacher accepted successfully";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "something bad happened";
    }
}
