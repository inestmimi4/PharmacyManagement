package com.example.pharmacymanagement.repositories;
import com.example.pharmacymanagement.models.DateExpirationDepasseException;
import com.example.pharmacymanagement.models.Medicament;
import com.example.pharmacymanagement.models.NegativPrixMedicament;
import com.example.pharmacymanagement.mysql_connection.MySqlConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class MedicamentRepository implements GenericRepository<Medicament> {

    @Override
    public void add(Medicament medicament) throws SQLException {
        String query = "INSERT INTO medicaments (nom, genre, prix, numeroSerie, dateExpiration) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, medicament.getNom());
            statement.setString(2, medicament.getGenre());
            statement.setDouble(3, medicament.getPrix());
            statement.setLong(4, medicament.getNumeroSerie());
            statement.setDate(5, Date.valueOf(medicament.getDateExpiration()));
            statement.executeUpdate();
        }
    }

    @Override
    public List<Medicament> getAll() throws SQLException {
        List<Medicament> medicaments = new ArrayList<>();
        String query = "SELECT * FROM medicaments";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Medicament medicament = new Medicament(
                        resultSet.getString("nom"),
                        resultSet.getString("genre"),
                        resultSet.getDouble("prix"),
                        resultSet.getLong("numeroSerie"),
                        resultSet.getDate("dateExpiration").toLocalDate()
                );
                medicament.setId(resultSet.getInt("code"));
                medicaments.add(medicament);
            }
        } catch (DateExpirationDepasseException | NegativPrixMedicament e) {
            throw new RuntimeException(e);
        }
        return medicaments;
    }

    @Override
    public void update(Medicament medicament) throws SQLException {
        String query = "UPDATE medicaments SET nom = ?, genre = ?, prix = ?, numeroSerie = ?, dateExpiration = ? WHERE code = ?";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, medicament.getNom());
            statement.setString(2, medicament.getGenre());
            statement.setDouble(3, medicament.getPrix());
            statement.setLong(4, medicament.getNumeroSerie());
            statement.setDate(5, Date.valueOf(medicament.getDateExpiration()));
            statement.setInt(6, medicament.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM medicaments WHERE code = ?";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<Medicament> searchMedicamentsByName(String name) throws SQLException {
        return getMedicamentsStream()
                .filter(m -> m.getNom().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    public List<Medicament> searchMedicamentsByCategory(String category) throws SQLException {
        return getMedicamentsStream()
                .filter(m -> m.getGenre().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Medicament> searchMedicamentsByFirstLetters(String letters) throws SQLException {
        return getMedicamentsStream()
                .filter(m -> m.getNom().startsWith(letters))
                .collect(Collectors.toList());
    }

    private Stream<Medicament> getMedicamentsStream() throws SQLException {
        List<Medicament> medicaments = new ArrayList<>();
        String query = "SELECT * FROM medicaments";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Medicament medicament = new Medicament(
                        resultSet.getString("nom"),
                        resultSet.getString("genre"),
                        resultSet.getDouble("prix"),
                        resultSet.getLong("numeroSerie"),
                        resultSet.getDate("dateExpiration").toLocalDate()
                );
                medicament.setId(resultSet.getInt("code"));
                medicaments.add(medicament);
            }
        } catch (DateExpirationDepasseException | NegativPrixMedicament e) {
            throw new RuntimeException(e);
        }
        return medicaments.stream();
    }
    public Optional<Integer> getIdByNom2(String medicament1) {
        String query = "SELECT code FROM medicaments WHERE nom = ?";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, medicament1);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getInt("code"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public int getIdByNom(String medicament1) {
        String query = "SELECT code FROM medicaments WHERE nom = ?";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, medicament1);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("code");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public Medicament getById(long medicationId) throws SQLException {
        String query = "SELECT * FROM medicaments WHERE code = ?";
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, medicationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Medicament(
                            resultSet.getString("nom"),
                            resultSet.getString("genre"),
                            resultSet.getDouble("prix"),
                            resultSet.getLong("numeroSerie"),
                            resultSet.getDate("dateExpiration").toLocalDate()
                    );
                }
            }
        } catch (DateExpirationDepasseException | NegativPrixMedicament e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}