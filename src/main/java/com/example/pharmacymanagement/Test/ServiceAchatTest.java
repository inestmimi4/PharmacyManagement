package com.example.pharmacymanagement.Test;

import com.example.pharmacymanagement.models.*;
import com.example.pharmacymanagement.services.AppareilMedicalService;
import com.example.pharmacymanagement.services.ClientService;
import com.example.pharmacymanagement.services.MedicamentService;
import com.example.pharmacymanagement.services.ServiceAchat;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class ServiceAchatTest {

    private ServiceAchat serviceAchat;
    private ClientService clientService;
    private MedicamentService medicamentService;
    private AppareilMedicalService appareilMedicalService;

    @Before
    public void setUp() {
        serviceAchat = new ServiceAchat();
        clientService = new ClientService();
        medicamentService = new MedicamentService();
        appareilMedicalService = new AppareilMedicalService();
    }

    private ClientFidele createOrRetrieveClient(String nom, String prenom, String email, String telephone, LocalDate dateAdhesion) {
        ClientFidele client = new ClientFidele(nom, prenom, email, telephone, dateAdhesion);
        try {
            clientService.addClient(client.getNom(), client.getPrenom(), client.getEmail(), client.getTelephone(), client.getDateAdhesion());
            client.setId(clientService.getClientIdByEmail(email));
        } catch (Exception e) {
            client.setId(clientService.getClientIdByEmail(email));
        }
        return client;
    }

    private AppareilMedical createOrRetrieveAppareilMedical(String nom, String marque, double prix, String numeroSerie, LocalDate dateAchat, int garantie, LocalDate dateMiseEnService, boolean disponible) {
        AppareilMedical appareilMedical = new AppareilMedical(nom, marque, prix, numeroSerie, dateAchat, garantie, dateMiseEnService, disponible);
        try {
            appareilMedicalService.addAppareilMedical(appareilMedical.getNom(), appareilMedical.getMarque(), appareilMedical.getPrix(), appareilMedical.getNumeroSerie(), appareilMedical.getDateAchat(), appareilMedical.getGarantie(), appareilMedical.getDateMiseEnService(), appareilMedical.isDisponible());
            appareilMedical.setId(appareilMedicalService.getAppareilMedicalIdByNumeroSerie(numeroSerie));
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            appareilMedical.setId(appareilMedicalService.getAppareilMedicalIdByNumeroSerie(numeroSerie));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appareilMedical;
    }

    private Medicament createOrRetrieveMedicament(String nom, String genre, double prix, long numeroSerie, LocalDate dateExpiration,String typeMedicament) throws DateExpirationDepasseException, NegativPrixMedicament {
        Medicament medicament = new Medicament(nom, genre, prix, numeroSerie, dateExpiration,typeMedicament);
        try {
            medicamentService.addMedicament(medicament.getNom(), medicament.getGenre(), medicament.getPrix(), medicament.getNumeroSerie(), medicament.getDateExpiration(), "chimique");
            medicament.setId(medicamentService.getMedicamentIdByName(nom));
        } catch (Exception e) {
            medicament.setId(medicamentService.getMedicamentIdByName(nom));
        }
        return medicament;
    }

    @Test
    public void testAddClientAndPurchaseAppareilMedical() {
        try {
            ClientFidele newClient = createOrRetrieveClient("Jane", "Smith", "jane.smith@example.com", "0987654321", LocalDate.of(2021, 5, 15));
            AppareilMedical newAppareilMedical = createOrRetrieveAppareilMedical("Appareil2", "Brand2", 600.0, "12345678901", LocalDate.now(), 12, LocalDate.now(), true);

            serviceAchat.acheterVendable(newClient, newAppareilMedical, 1, "Échelonné");
            System.out.println("New appareil medical purchased successfully with Échelonné payment method!");
        } catch (Exception e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }

    @Test
    public void testAddClientAndPurchaseMedicament() {
        try {
            ClientFidele newClient = createOrRetrieveClient("Jane", "Smith", "jane.smith@example.com", "0987654321", LocalDate.of(2021, 5, 15));
            Medicament newMedicament = createOrRetrieveMedicament("Medicament1", "Category1", 50.0, 12345678902L, LocalDate.now().plusMonths(6),"chimique");

            serviceAchat.acheterVendable(newClient, newMedicament, 2, "Échelonné");
            System.out.println("New medicament purchased successfully with Comptant payment method!");
        } catch (Exception e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }

    @Test
    public void testApplyDiscountOnExpiringMedicaments() {
        try {
            Medicament expiringMedicament = createOrRetrieveMedicament("ExpiringMedicament", "Category1", 100.0, 20000L, LocalDate.now().plusMonths(1),"chimique");

            List<Medicament> medicaments = serviceAchat.appliquerRemiseSurMedicamentsExpirant();
            assertFalse(medicaments.isEmpty());
            System.out.println("Medicaments with discount:");
            medicaments.forEach(System.out::println);
        } catch (Exception e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }
}