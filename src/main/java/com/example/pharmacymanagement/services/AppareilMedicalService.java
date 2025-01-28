package com.example.pharmacymanagement.services;
import com.example.pharmacymanagement.models.AppareilMedical;
import com.example.pharmacymanagement.repositories.AppareilMedicalRepository;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AppareilMedicalService {

    private final AppareilMedicalRepository appareilMedicalRepository = new AppareilMedicalRepository();

    public void addAppareilMedical(String nom, String marque, double prix, String numeroSerie, LocalDate dateFabrication, int garantieEnMois, LocalDate dateAchat, boolean disponible) throws SQLException {
        AppareilMedical appareil = new AppareilMedical(nom, marque, prix, numeroSerie, dateFabrication, garantieEnMois, dateAchat, disponible);
        appareilMedicalRepository.add(appareil);
    }

    public List<AppareilMedical> getAppareilsMedicals() throws SQLException {
        return appareilMedicalRepository.getAll();
    }

    public void updateAppareilMedical(long id, String nom, String marque, double prix, String numeroSerie, LocalDate dateFabrication, int garantieEnMois, LocalDate dateAchat, boolean disponible) throws SQLException {
        AppareilMedical appareil = new AppareilMedical(nom, marque, prix, numeroSerie, dateFabrication, garantieEnMois, dateAchat, disponible);
        appareil.setId(id);
        appareilMedicalRepository.update(appareil);
    }

    public void deleteAppareilMedical(int id) throws SQLException {
        appareilMedicalRepository.delete(id);
    }

    public long getAppareilMedicalIdByNumeroSerie(String number) {
        return appareilMedicalRepository.getIdByNumeroSerie(number);
    }

}