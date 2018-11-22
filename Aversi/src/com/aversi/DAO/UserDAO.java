package com.aversi.DAO;

import com.aversi.db.DatabaseConnectionFactory;
import com.aversi.model.User;
import com.aversi.model.UserType;

import java.sql.*;

public class UserDAO {
    public static int validate(String username, String password) throws SQLException {
        final Connection con = DatabaseConnectionFactory.getConFactory().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int validated = -1;
        try {
            final String sql = "SELECT * FROM users where username = ? AND password = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next())
                validated = rs.getInt("status");

            return validated;
        } finally {
            // no need to make duplicate method, using second DAO class closer
            DrugDAO.close(con, stmt, rs);
        }

    }

    public static void createUser(User user) throws SQLException {
        Connection con = DatabaseConnectionFactory.getConFactory().getConnection();

        try {
            final String sql = "INSERT INTO users(username, password, identity, name, surname, age, status)" +
                    " VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getID());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setInt(6, user.getAge());
            stmt.setInt(7, user.getUserType().getValue());

            stmt.execute();

            stmt.close();
        } finally {
            con.close();
        }
    }

    public static User getUserByName(String userName) throws SQLException {
        final Connection con = DatabaseConnectionFactory.getConFactory().getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        User user = new User();

        try {
            final String sql = "SELECT * FROM users where username='" + userName + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setID(rs.getString("identity"));
                user.setFirstName(rs.getString("name"));
                user.setLastName(rs.getString("surname"));
                user.setAge(rs.getInt("age"));
                user.setUserType((rs.getInt("status") == 0) ? UserType.USER : UserType.PHARMACIST);
            }

            return user;
        } finally {
            // same thing closing with other DAO closer
            DrugDAO.close(con, stmt, rs);
        }
    }

    public static boolean validateById(String id) throws SQLException {
        // todo: check if this works
        Connection con = DatabaseConnectionFactory.getConFactory().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean validateById = true;

        try {
            final String sql = "SELECT * FROM users where identity = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, id);

            rs = stmt.executeQuery();


            if (rs.next())
                validateById = false;

            return validateById;
        } finally {
            DrugDAO.close(con, stmt, rs);
        }
    }
}
