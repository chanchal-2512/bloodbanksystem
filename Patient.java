package com.example;

public class Patient {
    private String patientID;
    private String name;
    private String gender;
    private String bloodGroup;
    private int quantityRequired;
    private String hospitalID;

    public Patient(String patientID, String name, String gender, String bloodGroup, int quantityRequired, String hospitalID) {
        this.patientID = patientID;
        this.name = name;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.quantityRequired = quantityRequired;
        this.hospitalID = hospitalID;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public int getQuantityRequired() {
        return quantityRequired;
    }

    public String getHospitalID() {
        return hospitalID;
    }
}
