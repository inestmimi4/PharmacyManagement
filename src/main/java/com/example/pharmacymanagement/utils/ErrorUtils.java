// src/main/java/com/example/pharmacymanagement/utils/ErrorUtils.java
package com.example.pharmacymanagement.utils;

import javafx.scene.control.Alert;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrorUtils {
    private static final Logger LOGGER = Logger.getLogger(ErrorUtils.class.getName());

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void logError(String message, Exception e) {
        LOGGER.log(Level.SEVERE, message, e);
    }
}