package com.example.houseservice;

public class Year {
    String id,year;

    public Year() {
    }

    public Year(String id, String year) {
        this.id = id;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
