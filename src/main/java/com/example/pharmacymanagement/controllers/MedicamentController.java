package com.example.pharmacymanagement.controllers;

import com.example.pharmacymanagement.models.Medicament;
import com.example.pharmacymanagement.services.MedicamentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MedicamentController extends BaseController {


    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnSales;
    @FXML
    private TextField nomField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField prixField;
    @FXML
    private TextField typeMedicamentField;

    @FXML
    private TextField numeroSerieField;
    @FXML
    private TextField dateExpirationField;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Medicament> medicamentTable;
    @FXML
    private TableColumn<Medicament, Integer> idColumn;
    @FXML
    private TableColumn<Medicament, String> nomColumn;
    @FXML
    private TableColumn<Medicament, String> genreColumn;
    @FXML
    private TableColumn<Medicament, Double> prixColumn;
    @FXML
    private TableColumn<Medicament, Long> numeroSerieColumn;
    @FXML
    private TableColumn<Medicament, LocalDate> dateExpirationColumn;
    @FXML
    private TableColumn<Medicament, String> typeMedicamentColumn;

    private final MedicamentService medicamentService = new MedicamentService();
    private final ObservableList<Medicament> medicamentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        numeroSerieColumn.setCellValueFactory(new PropertyValueFactory<>("numeroSerie"));
        dateExpirationColumn.setCellValueFactory(new PropertyValueFactory<>("dateExpiration"));
        typeMedicamentColumn.setCellValueFactory(new PropertyValueFactory<>("typeMedicament"));
        medicamentTable.setItems(medicamentList);
        loadMedicaments();

        searchField.setOnKeyReleased(this::filterMedicaments);
    }

    @FXML
    public void addMedicament() {
        try {
            String nom = nomField.getText();
            String genre = genreField.getText();
            String prixText = prixField.getText();
            String numeroSerieText = numeroSerieField.getText();
            String dateExpirationText = dateExpirationField.getText();
            String typeMedicament = typeMedicamentField.getText();

            if (nom.isEmpty() || genre.isEmpty() || prixText.isEmpty() || numeroSerieText.isEmpty() || dateExpirationText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled!");
                return;
            }

            double prix;
            long numeroSerie;
            LocalDate dateExpiration;

            try {
                prix = Double.parseDouble(prixText);
                numeroSerie = Long.parseLong(numeroSerieText);
                dateExpiration = LocalDate.parse(dateExpirationText);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid number format!");
                return;
            }

            medicamentService.addMedicament(nom, genre, prix, numeroSerie, dateExpiration, typeMedicament);
            loadMedicaments();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Medicament added successfully!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error adding medicament: " + e.getMessage());
        }
    }

    @FXML
    public void updateMedicament() {
        try {
            Medicament selectedMedicament = medicamentTable.getSelectionModel().getSelectedItem();
            if (selectedMedicament != null) {
                String nom = nomField.getText();
                String genre = genreField.getText();
                double prix = Double.parseDouble(prixField.getText());
                long numeroSerie = Long.parseLong(numeroSerieField.getText());
                LocalDate dateExpiration = LocalDate.parse(dateExpirationField.getText());
                String typeMedicament = typeMedicamentField.getText();

                medicamentService.updateMedicament((int) selectedMedicament.getCode(), nom, genre, prix, numeroSerie, dateExpiration, typeMedicament);
                loadMedicaments();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Medicament updated successfully!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error updating medicament: " + e.getMessage());
        }
    }

    @FXML
    public void deleteMedicament() {
        try {
            Medicament selectedMedicament = medicamentTable.getSelectionModel().getSelectedItem();
            if (selectedMedicament != null) {
                boolean confirmed = showConfirmationAlert("Confirmation", "Confirm Deletion", "Are you sure you want to delete this client?");
                if (confirmed) {
                    medicamentService.deleteMedicament((int) selectedMedicament.getCode());
                    loadMedicaments();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Medicament deleted successfully!");
                }
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error deleting medicament: " + e.getMessage());
        }
    }

    private void loadMedicaments() {
        try {
            medicamentList.setAll(medicamentService.getMedicaments());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error loading medicaments: " + e.getMessage());
        }
    }

    private void filterMedicaments(KeyEvent event) {
        try {
            String searchText = searchField.getText().toLowerCase();
            List<Medicament> filteredList = medicamentService.getMedicaments().stream()
                    .filter(m -> m.getNom().toLowerCase().contains(searchText) ||
                            m.getGenre().toLowerCase().contains(searchText) ||
                            m.getNom().toLowerCase().startsWith(searchText))
                    .collect(Collectors.toList());
            medicamentList.setAll(filteredList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error filtering medicaments: " + e.getMessage());
        }
    }

    @FXML
    private Button btnmedicament;
    @FXML

    private Button btnSalesAppareil;


    @FXML
    private void handleClientsButtonClick() {
        loadInterface("/com/example/pharmacymanagement/views/ClientInterface.fxml", "Client Interface", btnCustomers);
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
    public void handleSalesButtonClick() {
        loadInterface("/com/example/pharmacymanagement/views/SalesInterface.fxml", "Sales Interface", btnSales);
    }

    @FXML
    public void handleSalesAppareilClick() {
        loadInterface("/com/example/pharmacymanagement/views/SalesAppareilMedical.fxml", "Sales Interface", btnSalesAppareil);
    }


}