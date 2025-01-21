package com.example.pharmacymanagement.controllers;

import com.example.pharmacymanagement.models.AppareilMedical;
import com.example.pharmacymanagement.models.ClientFidele;
import com.example.pharmacymanagement.models.VentesAppareils;
import com.example.pharmacymanagement.mysql_connection.MySqlConnection;
import com.example.pharmacymanagement.services.ServiceAchat;
import com.example.pharmacymanagement.utils.Alertable;
import com.example.pharmacymanagement.utils.DatabaseUtils;
import com.example.pharmacymanagement.utils.ErrorUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;


public class SalesAppareilMedicalController implements Alertable {

    @FXML
    private TextField clientIdField;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnmedicament;
    @FXML
    private Button btnSales;

    @FXML
    private TextField appareilIdField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField paymentModeField;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<VentesAppareils> salesTable;
    @FXML
    private TableColumn<VentesAppareils, Long> idColumn;
    @FXML
    private TableColumn<VentesAppareils, Long> clientIdColumn;
    @FXML
    private TableColumn<VentesAppareils, Long> appareilIdColumn;
    @FXML
    private TableColumn<VentesAppareils, Integer> quantityColumn;
    @FXML
    private TableColumn<VentesAppareils, Double> totalPriceColumn;
    @FXML
    private TableColumn<VentesAppareils, LocalDate> dateColumn;
    @FXML
    private TableColumn<VentesAppareils, String> paymentModeColumn;

    private final ServiceAchat serviceAchat = new ServiceAchat();
    private final ObservableList<VentesAppareils> salesList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        appareilIdColumn.setCellValueFactory(new PropertyValueFactory<>("appareilId"));
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
            long appareilId = Long.parseLong(appareilIdField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            String paymentMode = paymentModeField.getText();

            if (!clientExists(clientId)) {
                showError("Client ID does not exist.");
                return;
            }

            if (!appareilExists(appareilId)) {
                showError("Appareil ID does not exist.");
                return;
            }

            ClientFidele client = new ClientFidele();
            client.setId((int) clientId);

            AppareilMedical appareil = getAppareilById(appareilId);
            if (appareil == null) {
                showError("Appareil not found.");
                return;
            }

            double prixTotal = appareil.getPrix() * quantity;

            VentesAppareils vente = new VentesAppareils();
            vente.setClientId(clientId);
            vente.setAppareilId(appareilId);
            vente.setQuantity(quantity);
            vente.setTotalPrice(prixTotal);
            vente.setDate(LocalDate.now());
            vente.setPaymentMode(paymentMode);

            serviceAchat.acheterVendable(client, appareil, quantity, paymentMode);
            loadSales();
            showSuccess("Sale added successfully!");
        } catch (Exception e) {
            handleException("Error adding sale", e);
        }
    }

    private AppareilMedical getAppareilById(long appareilId) throws SQLException {
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM appareils_medicals WHERE idAppareil = ?")) {
            statement.setLong(1, appareilId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                AppareilMedical appareil = new AppareilMedical();
                appareil.setId(resultSet.getLong("idAppareil"));
                appareil.setNom(resultSet.getString("nom"));
                appareil.setMarque(resultSet.getString("marque"));
                appareil.setPrix(resultSet.getDouble("prix"));
                appareil.setNumeroSerie(resultSet.getString("numeroSerie"));
                appareil.setDateFabrication(resultSet.getDate("dateFabrication").toLocalDate());
                appareil.setGarantieEnMois(resultSet.getInt("garantieEnMois"));
                appareil.setDateAchat(resultSet.getDate("dateAchat").toLocalDate());
                appareil.setDisponible(resultSet.getBoolean("disponible"));
                return appareil;
            }
        }
        return null;
    }

    private boolean clientExists(long clientId) throws SQLException {
        return DatabaseUtils.recordExists("clients", "idClient", clientId);
    }

    private boolean appareilExists(long appareilId) throws SQLException {
        return DatabaseUtils.recordExists("appareils_medicals", "idAppareil", appareilId);
    }

    private void loadSales() {
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM ventes_appareils")) {
            ResultSet resultSet = statement.executeQuery();
            salesList.clear();
            while (resultSet.next()) {
                VentesAppareils sale = new VentesAppareils();
                sale.setId(resultSet.getLong("idVenteA"));
                sale.setClientId(resultSet.getLong("idClient"));
                sale.setAppareilId(resultSet.getLong("idAppareil"));
                sale.setQuantity(resultSet.getInt("quantite"));
                sale.setTotalPrice(resultSet.getDouble("prixTotal"));
                sale.setDate(resultSet.getDate("dateVente").toLocalDate());
                sale.setPaymentMode(resultSet.getString("modePaiement"));
                salesList.add(sale);
            }
        } catch (SQLException e) {
            handleException("Error loading sales", e);
        }
    }

    private void filterSales(KeyEvent event) {
        try {
            String searchText = searchField.getText().toLowerCase();
            ObservableList<VentesAppareils> filteredList = FXCollections.observableArrayList();

            for (VentesAppareils sale : salesList) {
                if (String.valueOf(sale.getClientId()).contains(searchText) ||
                        String.valueOf(sale.getAppareilId()).contains(searchText) ||
                        sale.getPaymentMode().toLowerCase().contains(searchText)) {
                    filteredList.add(sale);
                }
            }

            salesTable.setItems(filteredList);
        } catch (Exception e) {
            handleException("Error filtering sales", e);
        }
    }

    @FXML
    public void deleteSale() {
        VentesAppareils selectedSale = salesTable.getSelectionModel().getSelectedItem();
        if (selectedSale != null) {
            try (Connection connection = MySqlConnection.getMySqlConnection();
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM ventes_appareils WHERE idVenteA = ?")) {
                statement.setLong(1, selectedSale.getId());
                statement.executeUpdate();
                salesList.remove(selectedSale);
                showSuccess("Sale deleted successfully!");
            } catch (SQLException e) {
                handleException("Error deleting sale", e);
            }
        } else {
            showWarning("No sale selected.");
        }
    }

    @FXML
    public void updateSale() {
        VentesAppareils selectedSale = salesTable.getSelectionModel().getSelectedItem();
        if (selectedSale != null) {
            try (Connection connection = MySqlConnection.getMySqlConnection();
                 PreparedStatement statement = connection.prepareStatement("UPDATE ventes_appareils SET idClient = ?, idAppareil = ?, quantite = ?, prixTotal = ?, dateVente = ?, modePaiement = ? WHERE idVenteA = ?")) {
                statement.setLong(1, selectedSale.getClientId());
                statement.setLong(2, selectedSale.getAppareilId());
                statement.setInt(3, selectedSale.getQuantity());
                statement.setDouble(4, selectedSale.getTotalPrice());
                statement.setDate(5, Date.valueOf(selectedSale.getDate()));
                statement.setString(6, selectedSale.getPaymentMode());
                statement.setLong(7, selectedSale.getId());
                statement.executeUpdate();
                loadSales();
                showSuccess("Sale updated successfully!");
            } catch (SQLException e) {
                handleException("Error updating sale", e);
            }
        } else {
            showWarning("No sale selected.");
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
    private void loadInterface(String fxmlPath, String title, Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
        } catch (Exception e) {
            ErrorUtils.showAlert(Alert.AlertType.ERROR, "Error", "Error loading " + title);

        }
    }

    private void showError(String message) {
        ErrorUtils.showAlert(Alert.AlertType.ERROR, "Error", message);
    }

    private void showSuccess(String message) {
        ErrorUtils.showAlert(Alert.AlertType.INFORMATION, "Success", message);
    }

    private void showWarning(String message) {
        ErrorUtils.showAlert(Alert.AlertType.WARNING, "Warning", message);
    }

    private void handleException(String message, Exception e) {
        ErrorUtils.showAlert(Alert.AlertType.ERROR, "Error", message + ": " + e.getMessage());
        ErrorUtils.logError(message, e);
    }
}