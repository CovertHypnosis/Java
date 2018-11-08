package packt.book.ch4.dao;

import packt.book.ch4.bean.Course;
import packt.book.ch4.bean.Teacher;
import packt.book.ch4.db.connection.DatabaseConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    public static void addCourse(Course course) throws SQLException {
        Connection con = DatabaseConnectionFactory.getConnectionFactory().getConnection();
        try {
            final String sql = "INSERT INTO Course (name, credits) VALUES (?,?)";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, course.getName());
            stmt.setInt(2, course.getCredits());

            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next())
                course.setId(rs.getInt(1));

            rs.close();
            stmt.close();
        } finally {
            con.close();
        }
    }


    public static List<Course> getCourses() throws SQLException {
        final Connection con = DatabaseConnectionFactory.getConnectionFactory().getConnection();

        List<Course> courses = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            // create SQL statement using left outer join
            StringBuilder sb = new StringBuilder("SELECT Course.id as courseID, Course.name as courseName,")
                    .append("Course.credits as credits, Teacher.id as teacherId, Teacher.first_name as firstName,")
                    .append("Teacher.last_name as lastName, Teacher.designation as designation ")
                    .append("FROM Course LEFT OUTER JOIN Teacher on ")
                    .append("Course.Teacher_id=Teacher.id ")
                    .append("ORDER BY Course.name");

            // execute the query
            rs = stmt.executeQuery(sb.toString());

            // iterate over result set and create course objects
            // add them to course list
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("courseID"));
                course.setName(rs.getString("courseName"));
                course.setCredits(rs.getInt("credits"));
                courses.add(course);

                int teacherId = rs.getInt("teacherID");
                if (rs.wasNull())
                    continue;

                Teacher teacher = new Teacher();
                teacher.setId(teacherId);
                teacher.setFirstName(rs.getString("firstName"));
                teacher.setLastName(rs.getString("lastName"));
                teacher.setDesignation(rs.getString("designation"));
                course.setTeacher(teacher);
            }

            return courses;
        } finally {
            try { if(rs!=null) rs.close();} catch (SQLException e) {}
            try { if(stmt!=null) stmt.close();} catch (SQLException e) {}
            try { if(con!=null) con.close();} catch (SQLException e) {}
        }
    }
}
