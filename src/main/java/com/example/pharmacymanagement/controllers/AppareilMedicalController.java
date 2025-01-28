package com.example.pharmacymanagement.controllers;

import com.example.pharmacymanagement.models.AppareilMedical;
import com.example.pharmacymanagement.services.AppareilMedicalService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AppareilMedicalController extends BaseController {

    @FXML
    private TextField searchField;
    @FXML
    private ImageView searchIcon;
    @FXML
    private TableView<AppareilMedical> appareilTable;
    @FXML
    private TableColumn<AppareilMedical, Long> idColumn;
    @FXML
    private TableColumn<AppareilMedical, String> nomColumn;
    @FXML
    private TableColumn<AppareilMedical, String> marqueColumn;
    @FXML
    private TableColumn<AppareilMedical, Double> prixColumn;
    @FXML
    private TableColumn<AppareilMedical, String> numeroSerieColumn;
    @FXML
    private TableColumn<AppareilMedical, LocalDate> dateFabricationColumn;
    @FXML
    private TableColumn<AppareilMedical, Integer> garantieColumn;
    @FXML
    private TableColumn<AppareilMedical, LocalDate> dateAchatColumn;
    @FXML
    private TableColumn<AppareilMedical, Boolean> disponibleColumn;
    @FXML
    private TextField nomField;
    @FXML
    private TextField marqueField;
    @FXML
    private TextField prixField;
    @FXML
    private TextField numeroSerieField;
    @FXML
    private TextField dateFabricationField;
    @FXML
    private TextField garantieField;
    @FXML
    private TextField dateAchatField;
    @FXML
    private TextField disponibleField;

    private final AppareilMedicalService appareilMedicalService = new AppareilMedicalService();
    private final ObservableList<AppareilMedical> appareilList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        marqueColumn.setCellValueFactory(new PropertyValueFactory<>("marque"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        numeroSerieColumn.setCellValueFactory(new PropertyValueFactory<>("numeroSerie"));
        dateFabricationColumn.setCellValueFactory(new PropertyValueFactory<>("dateFabrication"));
        garantieColumn.setCellValueFactory(new PropertyValueFactory<>("garantieEnMois"));
        dateAchatColumn.setCellValueFactory(new PropertyValueFactory<>("dateAchat"));
        disponibleColumn.setCellValueFactory(new PropertyValueFactory<>("disponible"));
        appareilTable.setItems(appareilList);
        loadAppareils();

        searchField.setOnKeyReleased(this::filterAppareils);
    }

    @FXML
    public void addAppareil() {
        try {
            String nom = nomField.getText();
            String marque = marqueField.getText();
            String prixText = prixField.getText();
            String numeroSerieText = numeroSerieField.getText();
            String dateFabricationText = dateFabricationField.getText();
            String garantieText = garantieField.getText();
            String dateAchatText = dateAchatField.getText();
            String disponibleText = disponibleField.getText();

            if (nom.isEmpty() || marque.isEmpty() || prixText.isEmpty() || numeroSerieText.isEmpty() || dateFabricationText.isEmpty() || garantieText.isEmpty() || dateAchatText.isEmpty() || disponibleText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled!");
                return;
            }

            double prix;
            long numeroSerie;
            LocalDate dateFabrication;
            int garantieEnMois;
            LocalDate dateAchat;
            boolean disponible;

            try {
                prix = Double.parseDouble(prixText);
                numeroSerie = Long.parseLong(numeroSerieText);
                dateFabrication = LocalDate.parse(dateFabricationText);
                garantieEnMois = Integer.parseInt(garantieText);
                dateAchat = LocalDate.parse(dateAchatText);
                disponible = Boolean.parseBoolean(disponibleText);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid number format!");
                return;
            }

            appareilMedicalService.addAppareilMedical(nom, marque, prix, String.valueOf(numeroSerie), dateFabrication, garantieEnMois, dateAchat, disponible);
            loadAppareils();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Appareil added successfully!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error adding appareil: " + e.getMessage());
        }
    }

    @FXML
    public void updateAppareil() {
        try {
            AppareilMedical selectedAppareil = appareilTable.getSelectionModel().getSelectedItem();
            if (selectedAppareil != null) {
                String nom = nomField.getText();
                String marque = marqueField.getText();
                double prix = Double.parseDouble(prixField.getText());
                long numeroSerie = Long.parseLong(numeroSerieField.getText());
                LocalDate dateFabrication = LocalDate.parse(dateFabricationField.getText());
                int garantieEnMois = Integer.parseInt(garantieField.getText());
                LocalDate dateAchat = LocalDate.parse(dateAchatField.getText());
                boolean disponible = Boolean.parseBoolean(disponibleField.getText());

                appareilMedicalService.updateAppareilMedical(selectedAppareil.getId(), nom, marque, prix, String.valueOf(numeroSerie), dateFabrication, garantieEnMois, dateAchat, disponible);
                loadAppareils();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Appareil updated successfully!");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error updating appareil: " + e.getMessage());
        }
    }

    @FXML
    public void deleteAppareil() {
        try {
            AppareilMedical selectedAppareil = appareilTable.getSelectionModel().getSelectedItem();
            if (selectedAppareil != null) {
                boolean confirmed = showConfirmationAlert("Confirmation", "Confirm Deletion", "Are you sure you want to delete this appareil?");
                if (confirmed) {
                    appareilMedicalService.deleteAppareilMedical((int) selectedAppareil.getId());
                    loadAppareils();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Appareil deleted successfully!");
                }
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error deleting appareil: " + e.getMessage());
        }
    }

    private void loadAppareils() {
        try {
            appareilList.setAll(appareilMedicalService.getAppareilsMedicals());
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error loading appareils: " + e.getMessage());
        }
    }

    private void filterAppareils(KeyEvent event) {
        try {
            String searchText = searchField.getText().toLowerCase();
            List<AppareilMedical> filteredList = appareilMedicalService.getAppareilsMedicals().stream()
                    .filter(a -> a.getNom().toLowerCase().contains(searchText) ||
                            a.getMarque().toLowerCase().contains(searchText) ||
                            a.getNom().toLowerCase().startsWith(searchText))
                    .collect(Collectors.toList());
            appareilList.setAll(filteredList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error filtering appareils: " + e.getMessage());
        }
    }

    @FXML
    private Button btnmedicament;
    @FXML
    private Button btnSalesAppareil;

    @FXML
    private void handleClientsButtonClick() {
        loadInterface("/com/example/pharmacymanagement/views/ClientInterface.fxml", "Client Interface", btnmedicament);
    }

    @FXML
    private void handleMedicamentButtonClick() {
        loadInterface("/com/example/pharmacymanagement/views/MedicamentInterface.fxml", "Medicament Interface", btnmedicament);
    }

    @FXML
    private void handleHomeButtonClick() {
        loadInterface("/com/example/pharmacymanagement/views/HomeInterface.fxml", "Home Interface", btnmedicament);
    }

    @FXML
    public void handleSalesButtonClick(ActionEvent actionEvent) {
        loadInterface("/com/example/pharmacymanagement/views/SalesInterface.fxml", "Sales Interface", btnSalesAppareil);
    }

    @FXML
    public void handleSalesAppareilClick(ActionEvent actionEvent) {
        loadInterface("/com/example/pharmacymanagement/views/SalesAppareilMedical.fxml", "Sales Interface", btnSalesAppareil);
    }
    @FXML
    private Button btnappareil;
    @FXML
    public void handleAppareilButtonClick(ActionEvent actionEvent) {
        loadInterface("/com/example/pharmacymanagement/views/AppareilInterface.fxml", "Sales Interface", btnappareil);
    }
}