package com.example.pharmacymanagement.controllers;

import com.example.pharmacymanagement.models.ClientFidele;
import com.example.pharmacymanagement.models.Medicament;
import com.example.pharmacymanagement.models.VentesMedicaments;
import com.example.pharmacymanagement.mysql_connection.MySqlConnection;
import com.example.pharmacymanagement.services.ServiceAchat;
import com.example.pharmacymanagement.utils.Alertable;
import com.example.pharmacymanagement.utils.DatabaseUtils;
import com.example.pharmacymanagement.utils.ErrorUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.sql.*;
import java.time.LocalDate;


public class SalesMedicamentController extends BaseController implements Alertable {

    @FXML
    private TextField clientIdField;
    @FXML
    private TextField medicationIdField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField paymentModeField;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<VentesMedicaments> salesTable;
    @FXML
    private TableColumn<VentesMedicaments, Long> idColumn;
    @FXML
    private TableColumn<VentesMedicaments, Long> clientIdColumn;
    @FXML
    private TableColumn<VentesMedicaments, Long> medicationIdColumn;
    @FXML
    private TableColumn<VentesMedicaments, Integer> quantityColumn;
    @FXML
    private TableColumn<VentesMedicaments, Double> totalPriceColumn;
    @FXML
    private TableColumn<VentesMedicaments, LocalDate> dateColumn;
    @FXML
    private TableColumn<VentesMedicaments, String> paymentModeColumn;

    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnSales;
    @FXML
    private Button btnmedicament;

    private final ServiceAchat serviceAchat = new ServiceAchat();
    private final ObservableList<VentesMedicaments> salesList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        medicationIdColumn.setCellValueFactory(new PropertyValueFactory<>("medicationId"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        paymentModeColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMode"));
        salesTable.setItems(salesList);
        loadSales();

        searchField.setOnKeyReleased(this::filterSales);
    }


    @FXML
    public void addSale() {
        try {
            long clientId = Long.parseLong(clientIdField.getText());
            long medicationId = Long.parseLong(medicationIdField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            String paymentMode = paymentModeField.getText();

            if (!clientExists(clientId)) {
                ErrorUtils.showAlert(Alert.AlertType.ERROR, "Error", "Client ID does not exist.");
                return;
            }

            if (!medicationExists(medicationId)) {
                ErrorUtils.showAlert(Alert.AlertType.ERROR, "Error", "Medication ID does not exist.");
                return;
            }

            ClientFidele client = new ClientFidele();
            client.setId((int) clientId);

            Medicament medication = getMedicamentById(medicationId);
            if (medication == null) {
                ErrorUtils.showAlert(Alert.AlertType.ERROR, "Error", "Medication not found.");
                return;
            }

            double prixTotal = medication.getPrix() * quantity;

            VentesMedicaments vente = new VentesMedicaments();
            vente.setClientId(clientId);
            vente.setMedicationId(medicationId);
            vente.setQuantity(quantity);
            vente.setTotalPrice(prixTotal);
            vente.setDate(LocalDate.now());
            vente.setPaymentMode(paymentMode);

            serviceAchat.acheterVendable(client, medication, quantity, paymentMode);
            loadSales();
            ErrorUtils.showAlert(Alert.AlertType.INFORMATION, "Success", "Sale added successfully!");
        } catch (Exception e) {
            ErrorUtils.showAlert(Alert.AlertType.ERROR, "Error", "Error adding sale: " + e.getMessage());
            ErrorUtils.logError("Error adding sale", e);
        }
    }

    private Medicament getMedicamentById(long medicationId) throws SQLException {
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM medicaments WHERE code = ?")) {
            statement.setLong(1, medicationId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Medicament medicament = new Medicament();
                medicament.setId(resultSet.getInt("code"));
                medicament.setNom(resultSet.getString("nom"));
                medicament.setGenre(resultSet.getString("genre"));
                medicament.setPrix(resultSet.getDouble("prix"));
                medicament.setNumeroSerie(resultSet.getLong("numeroSerie"));
                medicament.setDateExpiration(resultSet.getDate("dateExpiration").toLocalDate());
                medicament.setTypeMedicament(resultSet.getString("type_medicament"));
                return medicament;
            }
        }
        return null;
    }

    private boolean clientExists(long clientId) throws SQLException {
        return DatabaseUtils.recordExists("clients", "idClient", clientId);
    }

    private boolean medicationExists(long medicationId) throws SQLException {
        return DatabaseUtils.recordExists("medicaments", "code", medicationId);
    }

    private void loadSales() {
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM ventes_medicaments")) {
            ResultSet resultSet = statement.executeQuery();
            salesList.clear();
            while (resultSet.next()) {
                VentesMedicaments sale = new VentesMedicaments();
                sale.setId(resultSet.getLong("idVenteM"));
                sale.setClientId(resultSet.getLong("idClient"));
                sale.setMedicationId(resultSet.getLong("idMedicament"));
                sale.setQuantity(resultSet.getInt("quantite"));
                sale.setTotalPrice(resultSet.getDouble("prixTotal"));
                sale.setDate(resultSet.getDate("dateVente").toLocalDate());
                sale.setPaymentMode(resultSet.getString("modePaiement"));
                salesList.add(sale);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error loading sales: " + e.getMessage());
        }
    }

    private void filterSales(KeyEvent event) {
        String searchText = searchField.getText().toLowerCase();
        ObservableList<VentesMedicaments> filteredList = FXCollections.observableArrayList();

        for (VentesMedicaments sale : salesList) {
            if (String.valueOf(sale.getClientId()).contains(searchText) ||
                    String.valueOf(sale.getMedicationId()).contains(searchText) ||
                    sale.getPaymentMode().toLowerCase().contains(searchText)) {
                filteredList.add(sale);
            }
        }

        salesTable.setItems(filteredList);
    }

    @FXML
    public void deleteSale() {
        VentesMedicaments selectedSale = salesTable.getSelectionModel().getSelectedItem();
        if (selectedSale != null) {
            try (Connection connection = MySqlConnection.getMySqlConnection();
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM ventes_medicaments WHERE idVenteM = ?")) {
                statement.setLong(1, selectedSale.getId());
                statement.executeUpdate();
                salesList.remove(selectedSale);
                ErrorUtils.showAlert(Alert.AlertType.INFORMATION, "Success", "Sale deleted successfully!");
            } catch (SQLException e) {
                ErrorUtils.showAlert(Alert.AlertType.ERROR, "Error", "Error deleting sale: " + e.getMessage());
                ErrorUtils.logError("Error deleting sale", e);
            }
        } else {
            ErrorUtils.showAlert(Alert.AlertType.WARNING, "Warning", "No sale selected.");
        }
    }

    @FXML
    public void updateSale() {
        VentesMedicaments selectedSale = salesTable.getSelectionModel().getSelectedItem();
        if (selectedSale != null) {
            try (Connection connection = MySqlConnection.getMySqlConnection();
                 PreparedStatement statement = connection.prepareStatement("UPDATE ventes_medicaments SET idClient = ?, idMedicament = ?, quantite = ?, prixTotal = ?, dateVente = ?, modePaiement = ? WHERE idVenteM = ?")) {
                statement.setLong(1, selectedSale.getClientId());
                statement.setLong(2, selectedSale.getMedicationId());
                statement.setInt(3, selectedSale.getQuantity());
                statement.setDouble(4, selectedSale.getTotalPrice());
                statement.setDate(5, Date.valueOf(selectedSale.getDate()));
                statement.setString(6, selectedSale.getPaymentMode());
                statement.setLong(7, selectedSale.getId());
                statement.executeUpdate();
                loadSales();
                ErrorUtils.showAlert(Alert.AlertType.INFORMATION, "Success", "Sale updated successfully!");
            } catch (SQLException e) {
                ErrorUtils.showAlert(Alert.AlertType.ERROR, "Error", "Error updating sale: " + e.getMessage());
                ErrorUtils.logError("Error updating sale", e);
            }
        } else {
            ErrorUtils.showAlert(Alert.AlertType.WARNING, "Warning", "No sale selected.");
        }
    }

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
    public void handleSalesButtonClick(ActionEvent actionEvent) {
        loadInterface("/com/example/pharmacymanagement/views/SalesInterface.fxml", "Sales Interface", btnSales);
    }

    @FXML
    public void handleSalesAppareilClick(ActionEvent actionEvent) {
        loadInterface("/com/example/pharmacymanagement/views/SalesAppareilMedical.fxml", "Sales Interface", btnSalesAppareil);
    }
}
