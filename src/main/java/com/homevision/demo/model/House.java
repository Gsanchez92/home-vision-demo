package com.homevision.demo.model;

public class House {

    private int id;
    private String address;
    private String homeowner;
    private int price;
    private String photoURL;

    // getters and setters for all fields

    public String getPhotoUrl() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // ... other getters and setters ...

}