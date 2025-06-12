package com.example;

// BloodBankDAO.java
import java.sql.*;
import java.util.*;

public class BloodBankDAO {
    private Connection conn;

    {
        try {
            conn = DBConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BloodBank> getAllBloodBanks() throws SQLException {
        List<BloodBank> list = new ArrayList<>();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Blood_Bank");
        while (rs.next()) {
            list.add(new BloodBank(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
        }
        return list;
    }

    public void addBloodBank(BloodBank b) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Blood_Bank VALUES (?, ?, ?, ?, ?)");
        ps.setString(1, b.getId());
        ps.setString(2, b.getName());
        ps.setString(3, b.getCity());
        ps.setString(4, b.getState());
        ps.setInt(5, b.getStock());
        ps.executeUpdate();
    }

    public void deleteBloodBank(String id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM Blood_Bank WHERE Blood_Bank_ID = ?");
        ps.setString(1, id);
        ps.executeUpdate();
    }
}

