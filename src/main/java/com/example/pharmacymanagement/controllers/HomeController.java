package com.example.pharmacymanagement.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeController {

    private static final Logger logger = Logger.getLogger(HomeController.class.getName());

    @FXML
    private Button btnMedicament;
    @FXML
    private Button btnClient;
    @FXML
    private void handleMedicamentButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pharmacymanagement/views/MedicamentInterface.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnMedicament.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Medicament Interface");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error loading Medicament Interface", e);
        }
    }
    @FXML
    private void handleClientButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pharmacymanagement/views/ClientInterface.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnClient.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Client Interface");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error loading Client Interface", e);
        }
    }
}