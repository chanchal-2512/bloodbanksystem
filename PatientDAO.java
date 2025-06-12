package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    private Connection getConnection() throws SQLException {
        // Update DB connection parameters as needed
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/bloodbank", "postgres", "abc");
    }

    public void addPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO Patient (patient_id, name, gender, blood_group, quantity_required, hospital_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getPatientID());
            stmt.setString(2, patient.getName());
            stmt.setString(3, patient.getGender());
            stmt.setString(4, patient.getBloodGroup());
            stmt.setInt(5, patient.getQuantityRequired());
            stmt.setString(6, patient.getHospitalID());
            stmt.executeUpdate();
        }
    }

    public void deletePatient(String patientId) throws SQLException {
        String sql = "DELETE FROM Patient WHERE patient_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientId);
            stmt.executeUpdate();
        }
    }

    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM Patient";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Patient(
                        rs.getString("patient_id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("blood_group"),
                        rs.getInt("quantity_required"),
                        rs.getString("hospital_id")
                ));
            }
        }
        return list;
    }
}
