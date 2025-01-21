package com.example.pharmacymanagement.services;

import com.example.pharmacymanagement.models.AppareilMedical;
import com.example.pharmacymanagement.models.ClientFidele;
import com.example.pharmacymanagement.models.Medicament;
import com.example.pharmacymanagement.models.Vendable;
import com.example.pharmacymanagement.mysql_connection.MySqlConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceAchat {

    public void acheterVendable(ClientFidele client, Vendable vendable, int quantite, String modePaiement) throws SQLException {
        double prixTotal = vendable.getPrix() * quantite;
        LocalDate dateAchat = LocalDate.now();

        String specificVenteQuery;

        if (vendable instanceof Medicament) {
            specificVenteQuery = "INSERT INTO ventes_medicaments (idClient, idMedicament, quantite, prixTotal, dateVente, modePaiement) VALUES (?, ?, ?, ?, ?, ?)";
        } else if (vendable instanceof AppareilMedical) {
            specificVenteQuery = "INSERT INTO ventes_appareils (idClient, idAppareil, quantite, prixTotal, dateVente, modePaiement) VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            throw new IllegalArgumentException("Unknown vendable type");
        }

        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement specificVenteStatement = connection.prepareStatement(specificVenteQuery)) {

            specificVenteStatement.setLong(1, client.getId());
            specificVenteStatement.setLong(2, vendable.getId());
            specificVenteStatement.setInt(3, quantite);
            specificVenteStatement.setDouble(4, prixTotal);
            specificVenteStatement.setDate(5, Date.valueOf(dateAchat));
            specificVenteStatement.setString(6, modePaiement);
            specificVenteStatement.executeUpdate();
        }

        if (modePaiement.equalsIgnoreCase("Échelonné")) {
            gererPaiementEchelonne(client, vendable, prixTotal);
        }
    }

    private void gererPaiementEchelonne(ClientFidele client, Vendable vendable, double prixTotal) throws SQLException {
        double montantEchelonne = vendable.getTranche();
        LocalDate datePremierEchelon = LocalDate.now().plusMonths(1);

        String query = "INSERT INTO paiements_echelonnes (idClient, montant, dateEcheance) VALUES (?, ?, ?)";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < 3; i++) {
                statement.setLong(1, client.getId());
                statement.setDouble(2, montantEchelonne);
                statement.setDate(3, Date.valueOf(datePremierEchelon.plusMonths(i)));
                statement.executeUpdate();
            }
        }
    }


    public List<Medicament> appliquerRemiseSurMedicamentsExpirant() throws SQLException {
        List<Medicament> medicaments = new ArrayList<>();
        LocalDate dateDansUnMois = LocalDate.now().plusMonths(1);

        String selectQuery = "SELECT * FROM medicaments WHERE dateExpiration = ?";
        String updateQuery = "UPDATE medicaments SET prix = prix * 0.7 WHERE code = ?";

        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

            selectStatement.setDate(1, Date.valueOf(dateDansUnMois));
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Medicament medicament = new Medicament();
                medicament.setId(resultSet.getInt("code"));
                medicament.setNom(resultSet.getString("nom"));
                medicament.setGenre(resultSet.getString("genre"));
                medicament.setPrix(resultSet.getDouble("prix"));
                medicament.setNumeroSerie(resultSet.getLong("numeroSerie"));
                medicament.setDateExpiration(resultSet.getDate("dateExpiration").toLocalDate());

                medicaments.add(medicament);

                updateStatement.setInt(1, medicament.getId());
                updateStatement.executeUpdate();
            }
        }

        return medicaments;
    }
}