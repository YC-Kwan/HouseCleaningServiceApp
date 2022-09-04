package com.example.houseservice;

public class Feedback {
    private String id, feedbacks, date, username,image;

    public Feedback(){

    }

    public Feedback(String id, String feedbacks, String date, String username, String image) {
        this.id = id;
        this.feedbacks = feedbacks;
        this.date = date;
        this.username = username;
        this.image = image;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(String feedbacks) {
        this.feedbacks = feedbacks;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

