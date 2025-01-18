package com.example.pharmacymanagement.views;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class ActionCellFactory implements Callback<TableColumn<Object, Void>, TableCell<Object, Void>> {
    public ActionCellFactory() {
        // No-argument constructor
    }

    @Override
    public TableCell<Object, Void> call(final TableColumn<Object, Void> param) {
        return new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            private final Button updateButton = new Button("Update");

            {
                deleteButton.getStyleClass().add("action-button");
                updateButton.getStyleClass().add("action-button");

                deleteButton.setOnAction(event -> {
                    // Handle delete action
                });

                updateButton.setOnAction(event -> {
                    // Handle update action
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actionButtons = new HBox(deleteButton, updateButton);
                    actionButtons.getStyleClass().add("action-buttons");
                    setGraphic(actionButtons);
                }
            }
        };
    }
}