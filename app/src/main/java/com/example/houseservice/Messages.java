package com.example.houseservice;

public class Messages {
    String message, dateTime;
    String senderId;
    long timeStamp;

    public Messages() {
    }

    public Messages(String message, String senderId, long timeStamp, String dateTime) {
        this.message = message;
        this.senderId = senderId;
        this.timeStamp = timeStamp;
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
