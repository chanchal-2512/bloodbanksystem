package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalDAO {
    private final String url = "jdbc:postgresql://localhost:5432/bloodbank";
    private final String user = "postgres";
    private final String password = "abc";

    public HospitalDAO() {
        try {
            Class.forName("org.postgresql.Driver");  // Load the driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Hospital> getAllHospitals() throws SQLException {
        List<Hospital> hospitals = new ArrayList<>();
        String query = "SELECT hospital_id, name, location FROM Hospital";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                hospitals.add(new Hospital(
                        rs.getString("hospital_id"),
                        rs.getString("name"),
                        rs.getString("location")
                ));
            }
        }
        return hospitals;
    }

    public void addHospital(Hospital hospital) throws SQLException {
        String query = "INSERT INTO Hospital (hospital_id, name, location) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, hospital.getId());
            pstmt.setString(2, hospital.getName());
            pstmt.setString(3, hospital.getLocation());
            pstmt.executeUpdate();
        }
    }

    public void deleteHospital(String id) throws SQLException {
        String query = "DELETE FROM Hospital WHERE hospital_id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }
}
