package com.example.pharmacymanagement.controllers;

import com.example.pharmacymanagement.models.ClientFidele;
import com.example.pharmacymanagement.services.ClientService;
import com.example.pharmacymanagement.utils.AlertConfirmation;
import com.example.pharmacymanagement.utils.Alertable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ClientController implements AlertConfirmation, Alertable {

    private static final Logger logger = Logger.getLogger(ClientController.class.getName());

    @FXML
    private Button btnCustomers;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField dateAdhesionField;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<ClientFidele> clientTable;
    @FXML
    private TableColumn<ClientFidele, Integer> idColumn;
    @FXML
    private TableColumn<ClientFidele, String> nomColumn;
    @FXML
    private TableColumn<ClientFidele, String> prenomColumn;
    @FXML
    private TableColumn<ClientFidele, String> emailColumn;
    @FXML
    private TableColumn<ClientFidele, String> telephoneColumn;
    @FXML
    private TableColumn<ClientFidele, LocalDate> dateAdhesionColumn;

    private ClientService clientService = new ClientService();
    private ObservableList<ClientFidele> clientList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        dateAdhesionColumn.setCellValueFactory(new PropertyValueFactory<>("dateAdhesion"));
        clientTable.setItems(clientList);
        loadClients();

        searchField.setOnKeyReleased(this::filterClients);
    }

    @FXML
    public void addClient() {
        try {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String telephone = telephoneField.getText();
            String dateAdhesionText = dateAdhesionField.getText();

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || telephone.isEmpty() || dateAdhesionText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled!");
                return;
            }

            LocalDate dateAdhesion;

            try {
                dateAdhesion = LocalDate.parse(dateAdhesionText);
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid date format!");
                return;
            }

            clientService.addClient(nom, prenom, email, telephone, dateAdhesion);
            loadClients();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Client added successfully!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error adding client: " + e.getMessage());
        }
    }

    @FXML
    public void updateClient() {
        try {
            ClientFidele selectedClient = clientTable.getSelectionModel().getSelectedItem();
            if (selectedClient != null) {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String email = emailField.getText();
                String telephone = telephoneField.getText();
                LocalDate dateAdhesion = LocalDate.parse(dateAdhesionField.getText());

                clientService.updateClient(selectedClient.getId(), nom, prenom, email, telephone, dateAdhesion);
                loadClients();
                showInformationAlert("Success", null, "Client updated successfully!");
            }
        } catch (Exception e) {
            showErrorAlert("Error", "Error updating client", e.getMessage());
        }
    }

    @FXML
    public void deleteClient() {
        try {
            ClientFidele selectedClient = clientTable.getSelectionModel().getSelectedItem();
            if (selectedClient != null) {
                boolean confirmed = showConfirmationAlert("Confirmation", "Confirm Deletion", "Are you sure you want to delete this client?");
                if (confirmed) {
                    clientService.deleteClient(selectedClient.getId());
                    loadClients();
                    showInformationAlert("Success", null, "Client deleted successfully!");
                }
            }
        } catch (Exception e) {
            showErrorAlert("Error", "Error deleting client", e.getMessage());
        }
    }

    private void loadClients() {
        try {
            clientList.setAll(clientService.getClients());
        } catch (Exception e) {
            showErrorAlert("Error", "Error loading clients", e.getMessage());
        }
    }

    private void filterClients(KeyEvent event) {
        try {
            String searchText = searchField.getText().toLowerCase();
            List<ClientFidele> filteredList = clientService.getClients().stream()
                    .filter(c -> c.getNom().toLowerCase().contains(searchText) ||
                            c.getEmail().toLowerCase().contains(searchText) ||
                            c.getNom().toLowerCase().startsWith(searchText))
                    .collect(Collectors.toList());
            clientList.setAll(filteredList);
        } catch (Exception e) {
            showErrorAlert("Error", "Error filtering clients", e.getMessage());
        }
    }

    @FXML
    private void handleClientsButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pharmacymanagement/views/ClientInterface.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnCustomers.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Client Interface");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error loading Client Interface", e);
        }
    }

    @FXML
    private Button btnmedicament;

    @FXML
    private void handleMedicamentButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pharmacymanagement/views/MedicamentInterface.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnmedicament.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Medicament Interface");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error loading Medicament Interface", e);
        }
    }

    @FXML
    private void handleHomeButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pharmacymanagement/views/HomeInterface.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnmedicament.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Home Interface");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error loading Home Interface", e);
        }
    }
}