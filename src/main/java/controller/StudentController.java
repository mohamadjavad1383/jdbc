package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentController {
    private static StudentController instance;
    private Connection connection;

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
            ResultSet rs = getResultSet(id, connection, "student");
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

    public String registerStudent(String sId, String cId, String pId) {
        try {
            ResultSet rs = getResultSet(sId, connection, "student");
            ResultSet rs2 = getResultSet(cId, connection, "course");
            ResultSet rs3 = getResultSet(pId, connection, "teacher");
            if (!rs.next()) return "student id " + sId + " does not exist";
            if (!rs2.next()) return "course id " + cId + " does not exist";
            if (!rs3.next()) return "teacher id " + pId + " does not exist";
            int cap = rs2.getInt("coursecapacity");

            PreparedStatement ps4 = connection.prepareStatement("SELECT * FROM teachercourse where id = ?");
            ps4.setString(1, pId + "-" + cId);
            ResultSet rs4 = ps4.executeQuery();
            if (!rs4.next()) return "course with ths teacher does not exist";


            PreparedStatement ps6 = connection.prepareStatement("SELECT * FROM studentcourse where courseid = ?");
            ps6.setString(1, pId + "-" + cId);
            ResultSet rs6 = ps6.executeQuery();
            int count = 0;
            while (rs6.next()) {
                if (rs6.getString("studentnumber").equals(sId))
                    return "student have this lesson already";
                count++;
            }
            if (count >= cap)
                return "capacity is full";

            

            PreparedStatement ps1 = connection.prepareStatement("INSERT INTO studentcourse VALUES(?, ?, ?)");
            ps1.setString(1, sId);
            ps1.setString(2, cId);
            ps1.setString(3, pId + "-" + cId);

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

    public String viewStudentGpa(String grade) {
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

    public String score(String sId, String cId, String grade) {
        try {
            ResultSet rs = getResultSet(sId, connection, "student");
            if (!rs.next()) return "student id " + sId + " does not exist";

            ResultSet rs2 = getResultSet(cId, connection, "course");
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

    private ResultSet getResultSet(String id, Connection connection, String table) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " where " + table +"Number = ?");
        ps.setString(1, id);
        return ps.executeQuery();
    }

}
