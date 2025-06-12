package com.example;
import java.sql.Date;
public class Donor {
    private String donorID;
    private String name;
    private Date dob;
    private int age;
    private String bloodGroup;
    private String address;
    private String ph_no;

    public Donor(String donorID, String name, Date dob, int age, String bloodGroup, String address, String ph_no) {
        this.donorID = donorID;
        this.name = name;
        this.dob = dob;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.ph_no = ph_no;
    }

    public String getDonorID() {
        return donorID;
    }

    public String getName() {
        return name;
    }

    public Date getDob() {
        return dob;
    }

    public int getAge() {
        return age;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getAddress() {
        return address;
    }

    public String getph_no() {
        return ph_no;
    }
}