package com.aversi.DAO;

import com.aversi.db.DatabaseConnectionFactory;
import com.aversi.model.Drug;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DrugDAO {
    public static List<Drug> getDrugs() throws SQLException {
        // initializing variables for database usage
        final Connection con = DatabaseConnectionFactory.getConFactory().getConnection();
        List<Drug> drugs = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;


        try {
            // make query,execute and return values
            final String sql = "SELECT * FROM drugs";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            makeList(drugs, rs);

            return drugs;
        } finally {
            close(con, stmt, rs);
        }

    }

    // will delete after
    public static List<Drug> getDrugs(String name) throws SQLException {
        final Connection con = DatabaseConnectionFactory.getConFactory().getConnection();
        List<Drug> drugs = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            final String sql = "SELECT * FROM drugs where LOWER(name) like LOWER('%" + name + "%')";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            makeList(drugs, rs);

            return drugs;
        } finally {
            close(con, stmt, rs);
        }

    }

    public static List<Drug> getDrugsByNameOrDescription(String name) throws SQLException {
        final Connection con = DatabaseConnectionFactory.getConFactory().getConnection();
        List<Drug> drugs = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            final String sql = "SELECT * FROM drugs where lower(description) or lower(name)" +
                    "like lower('%" + name +"%')";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            makeList(drugs, rs);

            return drugs;
        } finally {
            close(con, stmt, rs);
        }
    }

    public static Drug getDrug(String name) throws SQLException {
        final Connection con = DatabaseConnectionFactory.getConFactory().getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            final String sql = "SELECT * FROM drugs where name = '" + name +"'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            Drug drug = new Drug();

            while (rs.next()) {
                drug.setName(rs.getString("name"));
                drug.setPrice(rs.getDouble("price"));
                drug.setDescription(rs.getString("description"));
                drug.setQuantity(rs.getInt("quantity"));
            }

            return drug;
        } finally {
            close(con, stmt, rs);
        }

    }


    public static void addDrug(Drug drug) throws SQLException {
        final Connection con = DatabaseConnectionFactory.getConFactory().getConnection();
        try {
            final String sql = "INSERT INTO drugs(name, description, price, quantity) values (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, drug.getName());
            stmt.setString(2, drug.getDescription());
            stmt.setDouble(3, drug.getPrice());
            stmt.setInt(4, drug.getQuantity());

            stmt.execute();

            stmt.close();
        } finally {
            con.close();
        }
    }

    private static void makeList(List<Drug> drugs, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Drug drug = new Drug();
            drug.setName(rs.getString("name"));
            drug.setPrice(rs.getDouble("price"));
            drug.setDescription(rs.getString("description"));
            drug.setQuantity(rs.getInt("quantity"));

            drugs.add(drug);
        }
    }

    public static void close(Connection con, Statement stmt, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();

            if (stmt != null)
                stmt.close();

            if (con != null)
                con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateDrug(Drug drug, String origName) throws SQLException {
        final Connection con = DatabaseConnectionFactory.getConFactory().getConnection();

       try {
           final String sql = "UPDATE drugs set name=?, description=?, price=?, quantity=? where name=?";
           PreparedStatement stmt = con.prepareStatement(sql);
           stmt.setString(1, drug.getName());
           stmt.setString(2, drug.getDescription());
           stmt.setDouble(3, drug.getPrice());
           stmt.setInt(4, drug.getQuantity());
           stmt.setString(5, origName);

           stmt.executeUpdate();

           stmt.close();
       } finally {
           con.close();
       }

    }
}
