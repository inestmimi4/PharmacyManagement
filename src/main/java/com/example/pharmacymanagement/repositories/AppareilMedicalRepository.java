package com.example.pharmacymanagement.repositories;

import com.example.pharmacymanagement.models.AppareilMedical;
import com.example.pharmacymanagement.mysql_connection.MySqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppareilMedicalRepository implements GenericRepository<AppareilMedical> {

    private static final Logger logger = Logger.getLogger(AppareilMedicalRepository.class.getName());

    @Override
    public void add(AppareilMedical appareil) throws SQLException {
        String query = "INSERT INTO appareils_medicals (nom, marque, prix, numeroSerie, dateFabrication, garantieEnMois, dateAchat, disponible) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            setStatementParameters(statement, appareil);
            statement.executeUpdate();
        }
    }

    @Override
    public List<AppareilMedical> getAll() throws SQLException {
        List<AppareilMedical> appareils = new ArrayList<>();
        String query = "SELECT * FROM appareils_medicals";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                AppareilMedical appareil = mapResultSetToAppareilMedical(resultSet);
                appareils.add(appareil);
            }
        }
        return appareils;
    }

    @Override
    public void update(AppareilMedical appareil) throws SQLException {
        String query = "UPDATE appareils_medicals SET nom = ?, marque = ?, prix = ?, numeroSerie = ?, dateFabrication = ?, garantieEnMois = ?, dateAchat = ?, disponible = ? WHERE idAppareil = ?";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            setStatementParameters(statement, appareil);
            statement.setLong(9, appareil.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM appareils_medicals WHERE idAppareil = ?";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public long getIdByNom(String nom) {
        return getIdByField("nom", nom);
    }

    public long getIdByNumeroSerie(String numeroSerie) {
        return getIdByField("numeroSerie", numeroSerie);
    }

    private long getIdByField(String fieldName, String value) {
        String query = "SELECT idAppareil FROM appareils_medicals WHERE " + fieldName + " = ?";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("idAppareil");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error getting ID by " + fieldName, e);
        }
        return 0;
    }

    private void setStatementParameters(PreparedStatement statement, AppareilMedical appareil) throws SQLException {
        statement.setString(1, appareil.getNom());
        statement.setString(2, appareil.getMarque());
        statement.setDouble(3, appareil.getPrix());
        statement.setString(4, appareil.getNumeroSerie());
        statement.setDate(5, Date.valueOf(appareil.getDateFabrication()));
        statement.setInt(6, appareil.getGarantieEnMois());
        statement.setDate(7, Date.valueOf(appareil.getDateAchat()));
        statement.setBoolean(8, appareil.isDisponible());
    }

    private AppareilMedical mapResultSetToAppareilMedical(ResultSet resultSet) throws SQLException {
        AppareilMedical appareil = new AppareilMedical(
                resultSet.getString("nom"),
                resultSet.getString("marque"),
                resultSet.getDouble("prix"),
                resultSet.getString("numeroSerie"),
                resultSet.getDate("dateFabrication").toLocalDate(),
                resultSet.getInt("garantieEnMois"),
                resultSet.getDate("dateAchat").toLocalDate(),
                resultSet.getBoolean("disponible")
        );
        appareil.setId(resultSet.getLong("idAppareil"));
        return appareil;
    }
}