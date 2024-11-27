package com.example.unplashapp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class APIUtility {

    private static final String ACCESS_KEY = "Y5ZKavffd4GTYAWQZjzlaUslgZJZ-GxscFv09C1ULV4";

    public static List<ImageData> getImages(String query, String filter) {

        String search = query;
        if(search.isEmpty()) search = "w";
        String apiUrl = "https://api.unsplash.com/search/photos?page=1&query=" + search + "&client_id=" + ACCESS_KEY;

        // Add filter parameters if needed
        if (filter.equals("Nature")) {
            apiUrl += "&query=nature";
        } else if (filter.equals("Animals")) {
            apiUrl += "&query=animals";
        } else if (filter.equals("Architecture")) {
            apiUrl += "&query=architecture";
        }

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(url.openStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            // Parse JSON response
            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
            JsonArray results = jsonResponse.getAsJsonArray("results");

            List<ImageData> images = new ArrayList<>();

            for (int i = 0; i < results.size(); i++) {
                JsonObject photoObject = results.get(i).getAsJsonObject();

                String imageUrl = photoObject.getAsJsonObject("urls").get("regular").getAsString();
                String photographer = photoObject.getAsJsonObject("user").get("name").getAsString();
                String photoName = photoObject.get("id").getAsString();
                String description = photoObject.has("description") && !photoObject.get("description").isJsonNull()
                        ? photoObject.get("description").getAsString()
                        : "No description available";

                images.add(new ImageData(imageUrl, photographer, photoName, description));
            }

            return images;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static List<ImageData> getRandomImages() {
        String apiUrl = "https://api.unsplash.com/photos/random?count=10&client_id=" + ACCESS_KEY;
        return getRequest(apiUrl);
    }

    private static List<ImageData> getRequest(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(url.openStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            Gson gson = new Gson();
            return gson.fromJson(response.toString(), new TypeToken<List<ImageData>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
