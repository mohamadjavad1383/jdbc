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
            ResultSet rs = getResultSet(id, connection, "teacher");
            if (rs.next()) return "id " + id + " already exist";
            PreparedStatement ps1 = connection.prepareStatement("INSERT INTO teacher VALUES(?, ?)");
            ps1.setString(1, id);
            ps1.setString(2, name);

            try {
                ps1.executeUpdate();
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
            ResultSet rs = getResultSet(pId, connection, "teacher");
            ResultSet rs2 = getResultSet(cId, connection, "course");
            if (!rs.next()) return "teacher id " + pId + " does not exist";
            if (!rs2.next()) return "course id " + cId + " does not exist";
            PreparedStatement ps1 = connection.prepareStatement("INSERT INTO teachercourse VALUES(?, ?, ?)");
            ps1.setString(1, pId);
            ps1.setString(2, cId);
            ps1.setString(3, pId + "-" + cId);

            try {
                ps1.executeUpdate();
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
