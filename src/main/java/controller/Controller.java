package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    private static Controller instance;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null)
            instance = new Controller();
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

    public String addCourse(String id, String name, int capacity, Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM course where CourseNumber = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
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

    public String addTeacher(String id, String name, Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM teacher where TeacherNumber = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
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

    public String acceptTeacher(String pId, String cId, Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM teacher where TeacherNumber = ?");
            PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM course where courseNumber = ?");
            ps.setString(1, pId);
            ps2.setString(1, cId);
            ResultSet rs = ps.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            if (!rs.next()) return "teacher id " + pId + " does not exist";
            if (!rs2.next()) return "course id " + cId + " does not exist";
            PreparedStatement ps1 = connection.prepareStatement("INSERT INTO teachercourse VALUES(?, ?)");
            ps1.setString(1, pId);
            ps1.setString(2, cId);

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

    public String deleteCourse(String sId, String cId, Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM student where StudentNumber = ?");
            PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM course where courseNumber = ?");
            ps.setString(1, sId);
            ps2.setString(1, cId);
            ResultSet rs = ps.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            if (!rs.next()) return "student id " + sId + " does not exist";
            if (!rs2.next()) return "course id " + cId + " does not exist";
            PreparedStatement ps1 = connection.prepareStatement("DELETE FROM studentcourse where" +
                    " studentnumber = ? and coursenumber = ?");
            ps1.setString(1, sId);
            ps1.setString(2, cId);

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

    public String changeFavourite(String id, String favourite, Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM student where studentNumber = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
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

    public String viewAverage(String cId, Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM course where courseNumber = ?");
            ps.setString(1, cId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return "course id " + cId + " does not exist";
            PreparedStatement ps1 = connection.prepareStatement("SELECT AVG(grade) FROM studentcourse " +
                    "WHERE coursenumber = ?");
            ps1.setString(1, cId);

            try {
                ResultSet rs1 = ps1.executeQuery();
                if (!rs1.next())
                    return "no one has this course";
                return "avg is " + ((int)(rs1.getFloat("avg") * 100) / 100.0);
            } catch (Exception e) {
                return "couldn't add to db";
            }
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

    public String viewStudentCount(Connection connection) {
        try {
            Map<String, Integer> map = new HashMap<>();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM course");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String course = rs.getString("coursenumber");
                PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM student where favourite = ?");
                ps1.setString(1, course);
                ResultSet rs1 = ps1.executeQuery();
                int count = 0;
                while (rs1.next())
                    count++;
                map.put(course, count);
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, Integer> entry : map.entrySet())
                stringBuilder.append("course: ").append(entry.getKey()).
                        append("  num: ").append(entry.getValue()).append("\n");
            return String.valueOf(stringBuilder);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "something bad happened";
    }
}
