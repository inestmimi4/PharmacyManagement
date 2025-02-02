package com.example.pharmacymanagement.repositories;

import com.example.pharmacymanagement.models.Client;
import com.example.pharmacymanagement.models.ClientFidele;
import com.example.pharmacymanagement.mysql_connection.MySqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientRepository implements GenericRepository<ClientFidele> {

    private static final Logger logger = Logger.getLogger(ClientRepository.class.getName());

    @Override
    public void add(ClientFidele client) throws SQLException {
        String query = "INSERT INTO clients (nom, prenom, email, telephone, dateAdhesion) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            setStatementParameters(statement, client);
            statement.executeUpdate();
        }
    }

    @Override
    public List<ClientFidele> getAll() throws SQLException {
        List<ClientFidele> clients = new ArrayList<>();
        String query = "SELECT * FROM clients";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ClientFidele client = mapResultSetToClientFidele(resultSet);
                clients.add(client);
            }
        }
        return clients;
    }

    @Override
    public void update(ClientFidele client) throws SQLException {
        String query = "UPDATE clients SET nom = ?, prenom = ?, email = ?, telephone = ?, dateAdhesion = ? WHERE idClient = ?";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            setStatementParameters(statement, client);
            statement.setInt(6, client.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM clients WHERE idClient = ?";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public int getIdByEmail(String email) {
        return getIdByField(email);
    }

    private int getIdByField(String value) {
        String query = "SELECT idClient FROM clients WHERE " + "email" + " = ?";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("idClient");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error getting ID by " + "email", e);
        }
        return -1;
    }

    private void setStatementParameters(PreparedStatement statement, ClientFidele client) throws SQLException {
        statement.setString(1, client.getNom());
        statement.setString(2, client.getPrenom());
        statement.setString(3, client.getEmail());
        statement.setString(4, client.getTelephone());
        statement.setDate(5, Date.valueOf(client.getDateAdhesion()));
    }

    private ClientFidele mapResultSetToClientFidele(ResultSet resultSet) throws SQLException {
        ClientFidele client = new ClientFidele(
                resultSet.getString("nom"),
                resultSet.getString("prenom"),
                resultSet.getString("email"),
                resultSet.getString("telephone"),
                resultSet.getDate("dateAdhesion").toLocalDate()
        );
        client.setId(resultSet.getInt("idClient"));
        return client;
    }

    public Client getById(long clientId) {
        String query = "SELECT * FROM clients WHERE idClient = ?";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, clientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToClientFidele(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error getting client by ID", e);
        }
        return null;
    }
}