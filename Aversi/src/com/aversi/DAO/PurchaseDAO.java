package com.aversi.DAO;

import com.aversi.db.DatabaseConnectionFactory;
import com.aversi.model.Drug;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseDAO {
    // todo: need to recheck
    public static void sell(String username, String drugName, int qty) throws SQLException {
        final Connection con = DatabaseConnectionFactory.getConFactory().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String userID = "",drugID = "";
            // get drug for update purposes
            Drug drug = DrugDAO.getDrug(drugName);
            // get user id
            String sqlForUser = "SELECT id FROM users where username = ?";
            stmt = con.prepareStatement(sqlForUser);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            while (rs.next())
                userID = rs.getString("id");

            // get drug id
            String sqlForDrug = "SELECT id FROM drugs where name = ?";
            stmt = con.prepareStatement(sqlForDrug);
            stmt.setString(1, drugName);
            rs = stmt.executeQuery();
            while (rs.next())
                drugID = rs.getString("id");

            // insert row with qty
            String sqlForInsert = "INSERT INTO purchases(userID,drugID,quantity) values (?,?,?)" +
                    "on duplicate key update quantity=quantity+?";
            stmt = con.prepareStatement(sqlForInsert);
            stmt.setString(1, userID);
            stmt.setString(2, drugID);
            stmt.setInt(3, qty);
            stmt.setInt(4, qty);

            stmt.executeUpdate();

            //if nothing goes wrong, decrease drug count
            drug.setQuantity(drug.getQuantity() - qty);
            DrugDAO.updateDrug(drug, drugName);
        } finally {
            DrugDAO.close(con, stmt, rs);
        }
    }
}
