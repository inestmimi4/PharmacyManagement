package com.example.pharmacymanagement.Test;

import com.example.pharmacymanagement.models.AppareilMedical;
import com.example.pharmacymanagement.models.ClientFidele;
import com.example.pharmacymanagement.models.Medicament;
import com.example.pharmacymanagement.services.AppareilMedicalService;
import com.example.pharmacymanagement.services.ClientService;
import com.example.pharmacymanagement.services.MedicamentService;
import com.example.pharmacymanagement.services.ServiceAchat;

import java.time.LocalDate;
import java.util.List;

public class TestServiceAchat {
    public static void main(String[] args) {
        try {
            ServiceAchat serviceAchat = new ServiceAchat();
            ClientService clientService = new ClientService();
            MedicamentService medicamentService = new MedicamentService();
            AppareilMedicalService appareilMedicalService = new AppareilMedicalService();

            ClientFidele newClient = new ClientFidele("Jane", "Smith", "jane.smith@example.com", "0987654321", LocalDate.of(2021, 5, 15));
            try {
                clientService.addClient(newClient.getNom(), newClient.getPrenom(), newClient.getEmail(), newClient.getTelephone(), newClient.getDateAdhesion());
                newClient.setId(clientService.getClientIdByEmail("jane.smith@example.com"));
            } catch (java.sql.SQLIntegrityConstraintViolationException e) {
                newClient.setId(clientService.getClientIdByEmail("jane.smith@example.com"));
            }
            AppareilMedical newAppareilMedical = new AppareilMedical("Appareil2", "Brand2", 600.0, "12345678901", LocalDate.now(), 12, LocalDate.now(), true);
            try {
                appareilMedicalService.addAppareilMedical(newAppareilMedical.getNom(), newAppareilMedical.getMarque(), newAppareilMedical.getPrix(), newAppareilMedical.getNumeroSerie(), newAppareilMedical.getDateAchat(), newAppareilMedical.getGarantie(), newAppareilMedical.getDateMiseEnService(), newAppareilMedical.isDisponible());
                newAppareilMedical.setId(appareilMedicalService.getAppareilMedicalIdByNumeroSerie("12345678901"));
            } catch (java.sql.SQLIntegrityConstraintViolationException e) {
                newAppareilMedical.setId(appareilMedicalService.getAppareilMedicalIdByNumeroSerie("12345678901"));
            }
            serviceAchat.acheterVendable(newClient, newAppareilMedical, 1, "Échelonné");
            System.out.println("New appareil medical purchased successfully with Échelonné payment method!");

            Medicament expiringMedicament = new Medicament("ExpiringMedicament", "Category1", 100.0, 20000L, LocalDate.now().plusMonths(1));
            medicamentService.addMedicament(expiringMedicament.getNom(), expiringMedicament.getGenre(), expiringMedicament.getPrix(), expiringMedicament.getNumeroSerie(), expiringMedicament.getDateExpiration(), "chimique");
            expiringMedicament.setId(medicamentService.getMedicamentIdByName("ExpiringMedicament"));

            List<Medicament> medicaments = serviceAchat.appliquerRemiseSurMedicamentsExpirant();
            System.out.println("Medicaments with discount:");
            medicaments.forEach(System.out::println);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}