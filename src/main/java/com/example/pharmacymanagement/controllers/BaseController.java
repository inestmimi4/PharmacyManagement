package com.example.pharmacymanagement.controllers;

import com.example.pharmacymanagement.utils.AlertConfirmation;
import com.example.pharmacymanagement.utils.Alertable;
import com.example.pharmacymanagement.utils.ErrorUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class BaseController implements Alertable, AlertConfirmation {

    protected static final Logger logger = Logger.getLogger(BaseController.class.getName());

    protected void loadInterface(String fxmlPath, String title, Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
        } catch (Exception e) {
            ErrorUtils.showAlert(Alert.AlertType.ERROR, "Error", "Error loading " + title);
            logger.log(Level.SEVERE, "Error loading " + title, e);
        }
    }
}