package com.example.unplashapp;

public class Result {
    private Urls urls;
    private String description;
    private int likes;
    private User user;

    // Getters and setters

    // Custom getter for extracting the URL
    public String getUrl() {
        return urls != null ? urls.getRegular() : null;
    }

    public String getPhotographer() {
        return user != null ? user.getName() : null;
    }
}
