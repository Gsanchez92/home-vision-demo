package com.homevision.demo.model;

import java.util.List;

public class HouseApiResponse {

    public HouseApiResponse() {
    }

    private List<House> houses;
    private boolean ok;

    public HouseApiResponse(List<House> houses) {
        this.houses = houses;
    }

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}