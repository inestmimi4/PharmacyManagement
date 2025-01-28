package com.example.pharmacymanagement.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.almasb.fxgl.app.GameApplication.launch;

public class AppareilInterface  extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/pharmacymanagement/views/AppareilInterface.fxml"));
        primaryStage.setTitle("Gérer les clients");
        primaryStage.setScene(new Scene(root, 1350, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

