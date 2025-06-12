package com.example;
import java.sql.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonorDAO {
    private Connection getConnection() throws SQLException {
        // Update DB connection parameters as needed
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/bloodbank", "postgres", "abc");
    }

    public void addDonor(Donor donor) throws SQLException {
        String sql = "INSERT INTO Donor (donor_id, name, dob, age, blood_group, address, ph_no) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, donor.getDonorID());
            stmt.setString(2, donor.getName());
            stmt.setDate(3, donor.getDob());
            stmt.setInt(4, donor.getAge());
            stmt.setString(5, donor.getBloodGroup());
            stmt.setString(6, donor.getAddress());
            stmt.setString(7, donor.getph_no());
            stmt.executeUpdate();
        }
    }

    public void deleteDonor(String donorId) throws SQLException {
        String sql = "DELETE FROM Donor WHERE donor_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, donorId);
            stmt.executeUpdate();
        }
    }

    public List<Donor> getAllDonors() throws SQLException {
        List<Donor> list = new ArrayList<>();
        String sql = "SELECT * FROM Donor";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Donor(
                        rs.getString("donor_id"),
                        rs.getString("name"),
                        rs.getDate("dob"),
                        rs.getInt("age"),
                        rs.getString("blood_group"),
                        rs.getString("address"),
                        rs.getString("ph_no")
                ));
            }
        }
        return list;
    }
}
