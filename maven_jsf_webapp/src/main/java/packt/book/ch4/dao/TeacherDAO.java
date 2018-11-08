package packt.book.ch4.dao;

import packt.book.ch4.bean.Teacher;
import packt.book.ch4.db.connection.DatabaseConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    public void addTeacher(Teacher teacher) throws SQLException {
        // get connection from the connection pool
        Connection con = DatabaseConnectionFactory.getConnectionFactory().getConnection();
        try {
            String sql = "INSERT INTO Teacher(first_name,last_name,designation) VALUES(?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, teacher.getFirstName());
            stmt.setString(2, teacher.getLastName());
            stmt.setString(3, teacher.getDesignation());

            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next())
                teacher.setId(rs.getInt(1));

            rs.close();
            stmt.close();
        } finally {
            con.close();
        }
    }

    public List<Teacher> getTeachers() throws SQLException {
        Connection con = DatabaseConnectionFactory.getConnectionFactory().getConnection();

        List<Teacher> teachers = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();

            String sql = "SELECT * FROM Teacher ORDER BY first_name";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(rs.getInt("id"));
                teacher.setFirstName(rs.getString("first_name"));
                teacher.setLastName(rs.getString("last_name"));
                teacher.setDesignation(rs.getString("designation"));
                teachers.add(teacher);
            }

            return teachers;
        } finally {
            con.close();
        }
    }
}
