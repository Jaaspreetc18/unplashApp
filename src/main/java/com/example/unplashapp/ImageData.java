package com.example.unplashapp;

class ImageData {
    private String imageUrl;
    private String photographer;
    private String photoName;
    private String description;

    // Constructor
    public ImageData(String imageUrl, String photographer, String photoName, String description) {
        this.imageUrl = imageUrl;
        this.photographer = photographer;
        this.photoName = photoName;
        this.description = description;
    }

    // Getters
    public String getImageUrl() {
        return imageUrl;
    }

    public String getPhotographer() {
        return photographer;
    }

    public String getPhotoName() {
        return photoName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ImageData{" +
                "imageUrl='" + imageUrl + '\'' +
                ", photographer='" + photographer + '\'' +
                ", photoName='" + photoName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
