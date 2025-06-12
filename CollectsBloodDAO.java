package com.example;

import java.sql.*;
import java.util.*;

public class CollectsBloodDAO {
    private final String url = "jdbc:postgresql://localhost:5432/bloodbank";
    private final String user = "postgres";
    private final String password = "abc";

    public Map<String, Integer> getBloodGroupAvailability(String bloodGroup) throws SQLException {
        Map<String, Integer> availability = new HashMap<>();

        String sql = "SELECT Blood_Bank_ID, SUM(Quantity) AS total_quantity " +
                "FROM Collects_Blood " +
                "WHERE Blood_group = ? " +
                "GROUP BY Blood_Bank_ID " +
                "HAVING SUM(Quantity) > 0";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bloodGroup);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String bloodBankId = rs.getString("Blood_Bank_ID");
                    int quantity = rs.getInt("total_quantity");
                    availability.put(bloodBankId, quantity);
                }
            }
        }

        return availability;
    }
}
