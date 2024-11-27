package com.example.unplashapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {
    private int total;

    @SerializedName("total_pages")
    private int totalPages;

    private List<Result> results;
    // Getter for the list of results
    public List<Result> getResults() {
        return results;
    }
    // Getters and setters
}

