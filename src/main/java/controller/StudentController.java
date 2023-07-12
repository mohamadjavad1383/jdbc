package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentController extends Controller{
    private static StudentController instance;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private StudentController() {
    }

    public static StudentController getInstance() {
        if (instance == null)
            instance = new StudentController();
        return instance;
    }

    public String addStudent(String name, String id, String favourite, String grade) {
        try {
            ResultSet studentResult = getResultSet(id, connection, "student");
            if (studentResult.next()) return "id " + id + " already exist";

            if (Float.parseFloat(grade) > 20.0)
                return "grade is not valid";

            PreparedStatement studentQuery = connection.prepareStatement("INSERT INTO student VALUES(?, ?, ?, ?)");
            studentQuery.setString(1, id);
            studentQuery.setString(2, favourite);
            studentQuery.setString(3, name);
            studentQuery.setFloat(4, Float.parseFloat(grade));

            try {
                studentQuery.executeUpdate();
            } catch (Exception e) {
                return "couldn't add to db";
            }
            return "student added successfully";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "something bad happened";
    }

    public String registerStudent(String sId, String cId, String pId) {
        try {
            ResultSet studentResult = getResultSet(sId, connection, "student");
            ResultSet courseResult = getResultSet(cId, connection, "course");
            ResultSet teacherResult = getResultSet(pId, connection, "teacher");
            if (!studentResult.next()) return "student id " + sId + " does not exist";
            if (!courseResult.next()) return "course id " + cId + " does not exist";
            if (!teacherResult.next()) return "teacher id " + pId + " does not exist";
            int cap = courseResult.getInt("coursecapacity");

            if (checkCourseAndTeacher(cId, pId) != null) return checkCourseAndTeacher(cId, pId);

            Integer count = getCount(sId, cId, pId);
            if (count == null) return "student have this lesson already";
            if (count >= cap)
                return "capacity is full";

            PreparedStatement studentCourseQuery = connection.prepareStatement("INSERT INTO studentcourse VALUES(?, ?, ?)");
            studentCourseQuery.setString(1, sId);
            studentCourseQuery.setString(2, cId);
            studentCourseQuery.setString(3, pId + "-" + cId);

            try {
                studentCourseQuery.executeUpdate();
            } catch (Exception e) {
                return "couldn't add to db";
            }
            return "student registered successfully";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "something bad happened";
    }

    private String checkCourseAndTeacher(String cId, String pId) throws SQLException {
        PreparedStatement teacherCourseQuery = connection.prepareStatement("SELECT * FROM teachercourse where id = ?");
        teacherCourseQuery.setString(1, pId + "-" + cId);
        ResultSet rs4 = teacherCourseQuery.executeQuery();
        if (!rs4.next()) return "course with ths teacher does not exist";
        return null;
    }

    private Integer getCount(String sId, String cId, String pId) throws SQLException {
        PreparedStatement studentCourseQuery = connection.prepareStatement("SELECT * FROM studentcourse where courseid = ?");
        studentCourseQuery.setString(1, pId + "-" + cId);
        ResultSet rs6 = studentCourseQuery.executeQuery();
        int count = 0;
        while (rs6.next()) {
            if (rs6.getString("studentnumber").equals(sId))
                return null;
            count++;
        }
        return count;
    }

    public String viewStudentGpa(String grade) {
        try {
            if (Float.parseFloat(grade) > 20.0)
                return "invalid grade";
            PreparedStatement studentQuery = connection.prepareStatement("SELECT * FROM student where grade >= ?");
            studentQuery.setFloat(1, Float.parseFloat(grade));
            ResultSet studentResult = studentQuery.executeQuery();
            int count = 0;
            while (studentResult.next())
                count++;
            return String.valueOf(count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "something bad happened";
    }

    public String score(String sId, String cId, String grade) {
        try {
            ResultSet studentResult = getResultSet(sId, connection, "student");
            if (!studentResult.next()) return "student id " + sId + " does not exist";

            ResultSet courseResult = getResultSet(cId, connection, "course");
            if (!courseResult.next()) return "course id " + cId + " does not exist";

            PreparedStatement studentCourseQuery = connection.prepareStatement("UPDATE studentcourse SET grade = ? " +
                    "WHERE studentnumber = ? and coursenumber = ?");
            studentCourseQuery.setFloat(1, Float.parseFloat(grade));
            studentCourseQuery.setString(2, sId);
            studentCourseQuery.setString(3, cId);

            try {
                int num = studentCourseQuery.executeUpdate();
                if (num == 0)
                    return "student does not have this course";
            } catch (Exception e) {
                return "couldn't add to db";
            }
            return "score added successfully";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "something bad happened";
    }
}
