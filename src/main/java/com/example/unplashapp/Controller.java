package com.example.unplashapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Controller {

    @FXML
    private TextField searchTextField;

    @FXML
    private ChoiceBox<String> filterChoiceBox;

    @FXML
    private VBox imageContainer;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private void initialize() {
        filterChoiceBox.getItems().addAll("All", "Nature", "Animals", "Architecture");
        filterChoiceBox.setValue("All");
       // fetchRandomImages();
        fetchImages();
    }

    @FXML
    private void fetchImages() {
        String query = searchTextField.getText();

        String filter = filterChoiceBox.getValue();
        progressIndicator.setVisible(true);

        CompletableFuture.supplyAsync(() -> APIUtility.getImages(query, filter))
                .thenAccept(response -> {

                    Platform.runLater(() -> {
                        if (response != null) {

                            displayImages(response);
                        } else {
                            showAlert("Error", "Failed to load images.");
                        }
                        progressIndicator.setVisible(false);
                    });
                });
    }

    private void displayImages(List<ImageData> images) {
        imageContainer.getChildren().clear();

        // Limit the loop to 10 images
        int limit = 10;
        for (int i = 0; i < limit; i++) {
            ImageData image = images.get(i);
            ImageView imageView = new ImageView(new Image(image.getImageUrl()));
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(event -> showImageDetails(image));
            imageContainer.getChildren().add(imageView);
        }
    }

    private void showImageDetails(ImageData image) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Image Details");
        alert.setHeaderText(image.getPhotoName());
        alert.setContentText("Photographer: " + image.getPhotographer() + "\nDescription: " + image.getDescription());
        alert.showAndWait();
    }

   private void fetchRandomImages() {
        progressIndicator.setVisible(true);
        CompletableFuture.supplyAsync(() -> APIUtility.getRandomImages())
                .thenAccept(response -> {
                    Platform.runLater(() -> {
                        if (response != null) {
                            displayImages(response);
                        } else {
                            showAlert("Error", "Failed to load images.");
                        }
                        progressIndicator.setVisible(false);
                    });
                });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
