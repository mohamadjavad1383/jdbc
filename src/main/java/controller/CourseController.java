package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseController extends Controller{
    private static CourseController instance;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private CourseController() {
    }

    public static CourseController getInstance() {
        if (instance == null)
            instance = new CourseController();
        return instance;
    }

    public String addCourse(String id, String name, int capacity) {
        try {
            ResultSet courseResult = getResultSet(id, connection, "course");
            if (courseResult.next()) return "id " + id + " already exist";
            PreparedStatement ps1 = connection.prepareStatement("INSERT INTO course VALUES(?, ?, ?)");
            ps1.setString(1, id);
            ps1.setString(2, name);
            ps1.setInt(3, capacity);

            try {
                ps1.executeUpdate();
            } catch (Exception e) {
                return "couldn't add to db";
            }
            return "course added successfully";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "something bad happened";
    }

    public String deleteCourse(String sId, String cId, String pId) {
        try {
            ResultSet studentResult = getResultSet(sId, connection, "student");
            ResultSet courseResult = getResultSet(cId, connection, "course");
            if (!studentResult.next()) return "student id " + sId + " does not exist";
            if (!courseResult.next()) return "course id " + cId + " does not exist";
            PreparedStatement studentCourseQuery = connection.prepareStatement("DELETE FROM studentcourse where" +
                    " studentnumber = ? and courseid = ?");
            studentCourseQuery.setString(1, sId);
            studentCourseQuery.setString(2, pId + "-" + cId);

            try {
                int num = studentCourseQuery.executeUpdate();
                if (num == 0)
                    return "student does not have this course";
                return "course deleted successfully";
            } catch (Exception e) {
                return "couldn't add to db";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "something bad happened";
    }

    public String changeFavourite(String id, String favourite) {
        try {
            ResultSet studentResult = getResultSet(id, connection, "student");
            if (!studentResult.next()) return "student id " + id + " does not exist";
            PreparedStatement studentQuery = connection.prepareStatement("UPDATE student SET favourite = ? WHERE studentnumber = ?");
            studentQuery.setString(1, favourite);
            studentQuery.setString(2, id);

            try {
                studentQuery.executeUpdate();
            } catch (Exception e) {
                return "error";
            }
            return "favourite changed successfully";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "something bad happened";
    }

    public String viewStudentCount() {
        try {
            PreparedStatement studentCourseQuery = connection.prepareStatement("SELECT courseid, count(distinct studentnumber) " +
                    "as num FROM studentcourse group by courseid");
            ResultSet studentCourseResult = studentCourseQuery.executeQuery();
            StringBuilder stringBuilder = new StringBuilder();
            while (studentCourseResult.next())
                stringBuilder.append("course: " + studentCourseResult.getString("courseid") + "  num: " +
                        studentCourseResult.getInt("num") + "\n");
            return String.valueOf(stringBuilder);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "something bad happened";
    }

    public String viewAverage(String cId, String pId) {
        try {
            ResultSet courseResult = getResultSet(cId, connection, "course");
            if (!courseResult.next()) return "course id " + cId + " does not exist";
            PreparedStatement studentCourseQuery = connection.prepareStatement("SELECT AVG(grade) FROM studentcourse " +
                    "WHERE courseid = ?");
            studentCourseQuery.setString(1, pId + "-" + cId);

            try {
                ResultSet studentCourseResult = studentCourseQuery.executeQuery();
                if (!studentCourseResult.next())
                    return "no one has this course";
                return "avg is " + ((int) (studentCourseResult.getFloat("avg") * 100) / 100.0);
            } catch (Exception e) {
                return "couldn't add to db";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "something bad happened";
    }
}
