package com.example.pharmacymanagement.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeInterface extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/pharmacymanagement/views/HomeInterface.fxml"));
        primaryStage.setTitle("Home Interface");
        primaryStage.setScene(new Scene(root, 1300, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
