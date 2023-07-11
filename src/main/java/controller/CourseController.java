package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseController {
    private static CourseController instance;
    private Connection connection;

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
            ResultSet rs = getResultSet(id, connection, "course");
            if (rs.next()) return "id " + id + " already exist";
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
            ResultSet rs = getResultSet(sId, connection, "student");
            ResultSet rs2 = getResultSet(cId, connection, "course");
            if (!rs.next()) return "student id " + sId + " does not exist";
            if (!rs2.next()) return "course id " + cId + " does not exist";
            PreparedStatement ps1 = connection.prepareStatement("DELETE FROM studentcourse where" +
                    " studentnumber = ? and courseid = ?");
            ps1.setString(1, sId);
            ps1.setString(2, pId + "-" + cId);

            try {
                int num = ps1.executeUpdate();
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
            ResultSet rs = getResultSet(id, connection, "student");
            if (!rs.next()) return "student id " + id + " does not exist";
            PreparedStatement ps1 = connection.prepareStatement("UPDATE student SET favourite = ? WHERE studentnumber = ?");
            ps1.setString(1, favourite);
            ps1.setString(2, id);

            try {
                ps1.executeUpdate();
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
            PreparedStatement ps = connection.prepareStatement("SELECT courseid, count(distinct studentnumber) as num FROM studentcourse group by courseid");
            ResultSet rs = ps.executeQuery();
            StringBuilder stringBuilder = new StringBuilder();
            while (rs.next())
                stringBuilder.append("course: " + rs.getString("courseid") + "  num: " + rs.getInt("num") + "\n");
            return String.valueOf(stringBuilder);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "something bad happened";
    }

    public String viewAverage(String cId, String pId) {
        try {
            ResultSet rs = getResultSet(cId, connection, "course");
            if (!rs.next()) return "course id " + cId + " does not exist";
            PreparedStatement ps1 = connection.prepareStatement("SELECT AVG(grade) FROM studentcourse " +
                    "WHERE courseid = ?");
            ps1.setString(1, pId + "-" + cId);

            try {
                ResultSet rs1 = ps1.executeQuery();
                if (!rs1.next())
                    return "no one has this course";
                return "avg is " + ((int) (rs1.getFloat("avg") * 100) / 100.0);
            } catch (Exception e) {
                return "couldn't add to db";
            }
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
