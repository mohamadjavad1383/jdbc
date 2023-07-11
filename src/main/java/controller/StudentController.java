package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentController {
    private static StudentController instance;

    private StudentController() {
    }

    public static StudentController getInstance() {
        if (instance == null)
            instance = new StudentController();
        return instance;
    }

    public String addStudent(String name, String id, String favourite, String grade, Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM student where studentnumber = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return "id " + id + " already exist";

            if (Float.parseFloat(grade) > 20.0)
                return "grade is not valid";

            PreparedStatement ps1 = connection.prepareStatement("INSERT INTO student VALUES(?, ?, ?, ?)");
            ps1.setString(1, id);
            ps1.setString(2, favourite);
            ps1.setString(3, name);
            ps1.setFloat(4, Float.parseFloat(grade));

            try {
                ps1.executeUpdate();
            } catch (Exception e) {
                return "couldn't add to db";
            }
            return "student added successfully";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "something bad happened";
    }

    public String registerStudent(String sId, String cId, Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM student where studentNumber = ?");
            PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM course where courseNumber = ?");
            ps.setString(1, sId);
            ps2.setString(1, cId);
            ResultSet rs = ps.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            if (!rs.next()) return "student id " + sId + " does not exist";
            if (!rs2.next()) return "course id " + cId + " does not exist";
            PreparedStatement ps1 = connection.prepareStatement("INSERT INTO studentcourse VALUES(?, ?)");
            ps1.setString(1, sId);
            ps1.setString(2, cId);

            try {
                ps1.executeUpdate();
            } catch (Exception e) {
                return "couldn't add to db";
            }
            return "student registered successfully";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "something bad happened";
    }

    public String viewStudentGpa(String grade, Connection connection) {
        try {
            if (Float.parseFloat(grade) > 20.0)
                return "invalid grade";
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM student where grade >= ?");
            ps.setFloat(1, Float.parseFloat(grade));
            ResultSet rs = ps.executeQuery();
            int count = 0;
            while (rs.next())
                count++;
            return String.valueOf(count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "something bad happened";
    }

    public String score(String sId, String cId, String grade, Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM student where studentNumber = ?");
            ps.setString(1, sId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return "student id " + sId + " does not exist";

            PreparedStatement ps3 = connection.prepareStatement("SELECT * FROM course where courseNumber = ?");
            ps3.setString(1, cId);
            ResultSet rs2 = ps3.executeQuery();
            if (!rs2.next()) return "course id " + cId + " does not exist";

            PreparedStatement ps2 = connection.prepareStatement("UPDATE studentcourse SET grade = ? " +
                    "WHERE studentnumber = ? and coursenumber = ?");
            ps2.setFloat(1, Float.parseFloat(grade));
            ps2.setString(2, sId);
            ps2.setString(3, cId);

            try {
                int num = ps2.executeUpdate();
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
