package com.homevision.demo.model;

public class House {
    public House() {}
    private int id;
    private String address;

    private String homeowner;
    private int price;
    private String photoURL;

    public String getPhotoURL() {
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

    public String getHomeowner() {
        return homeowner;
    }

    public int getPrice() {
        return price;
    }

    public void setHomeowner(String homeowner) {
        this.homeowner = homeowner;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    // ... other getters and setters ...

}