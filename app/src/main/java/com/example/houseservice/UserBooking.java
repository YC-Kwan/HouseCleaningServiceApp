package com.example.houseservice;

public class UserBooking {
    public String id,username,service,address,time,date,phoneNo;
    private int price,hour;

    public UserBooking() {

    }

    public UserBooking(String id,String username, String service, String address, String time, String date, String phoneNo, int price, int hour) {
        this.id = id;
        this.username=username;
        this.service = service;
        this.address = address;
        this.time = time;
        this.date = date;
        this.phoneNo = phoneNo;
        this.price = price;
        this.hour = hour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
