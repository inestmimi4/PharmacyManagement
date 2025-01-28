package com.example.pharmacymanagement.services;

import com.example.pharmacymanagement.models.Medicament;
import com.example.pharmacymanagement.repositories.MedicamentRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MedicamentService {

    private final MedicamentRepository medicamentRepository = new MedicamentRepository();


    public void addMedicament(String nom, String genre, double prix, long numeroSerie, LocalDate dateExpiration,String typeMedicament) throws Exception {
        Medicament medicament = new Medicament(nom, genre, prix, numeroSerie, dateExpiration,typeMedicament);
        medicamentRepository.add(medicament);
    }

    public List<Medicament> getMedicaments() throws Exception {
        return medicamentRepository.getAll();
    }

    public void updateMedicament(int id, String nom, String genre, double prix, long numeroSerie, LocalDate dateExpiration,String typeMedicament) throws Exception {
        Medicament medicament = new Medicament(nom, genre, prix, numeroSerie, dateExpiration,typeMedicament);
        medicament.setId(id);
        medicamentRepository.update(medicament);
    }

    public void deleteMedicament(int id) throws Exception {
        medicamentRepository.delete(id);
    }
    public List<Medicament> searchMedicamentsByName(String name) throws SQLException {
        return medicamentRepository.searchMedicamentsByName(name);
    }

    public List<Medicament> searchMedicamentsByCategory(String category) throws SQLException {
        return medicamentRepository.searchMedicamentsByCategory(category);
    }

    public List<Medicament> searchMedicamentsByFirstLetters(String letters) throws SQLException {
        return medicamentRepository.searchMedicamentsByFirstLetters(letters);
    }

    public int getMedicamentIdByName(String medicament1) {
        return medicamentRepository.getIdByNom(medicament1);
    }


}