module com.example.unplashapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.unplashapp to javafx.fxml;
    exports com.example.unplashapp;
}