package com.example.pharmacymanagement.utils;

import javafx.scene.control.Alert;

public interface Alertable {
    default void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
