package com.example;

public class BloodBank {
    private String id, name, city, state;
    private int stock;
    public BloodBank(String id, String name, String city, String state, int stock) {
        this.id = id; this.name = name; this.city = city; this.state = state; this.stock = stock;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public int getStock() { return stock; }
}
