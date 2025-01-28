package com.example.pharmacymanagement.Test;

import com.example.pharmacymanagement.services.MedicamentService;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class TestMedicamentService {

    private MedicamentService medicamentService;

    @Before
    public void setUp() {
        medicamentService = new MedicamentService();
    }

    @Test
    public void testAddMedicament() {
        try {
            medicamentService.addMedicament("Med1", "Genre1", 100.0, 123456789, LocalDate.now().plusMonths(1), "homeopathique");
            assertNotNull(medicamentService.searchMedicamentsByName("Med1"));
        } catch (Exception e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateMedicament() {
        try {
            medicamentService.addMedicament("Med1", "Genre1", 100.0, 123456789, LocalDate.now().plusMonths(1), "homeopathique");
            int medicamentId = medicamentService.searchMedicamentsByName("Med1").getFirst().getId();

            assertFalse(medicamentService.searchMedicamentsByName("Med1").isEmpty());

            medicamentService.updateMedicament(medicamentId, "Med1 Updated", "Genre1", 150.0, 123456789, LocalDate.now().plusMonths(2),"chimique");

            assertFalse(medicamentService.searchMedicamentsByName("Med1 Updated").isEmpty());
            assertEquals("Med1 Updated", medicamentService.searchMedicamentsByName("Med1 Updated").getFirst().getNom());
        } catch (Exception e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }

    @Test
    public void testSearchByName() {
        try {
            medicamentService.addMedicament("Med1", "Genre1", 100.0, 123456789, LocalDate.now().plusMonths(1), "homeopathique");
            assertFalse(medicamentService.searchMedicamentsByName("Med1").isEmpty());
        } catch (Exception e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }

    @Test
    public void testSearchByCategory() {
        try {
            medicamentService.addMedicament("Med1", "Genre1", 100.0, 123456789, LocalDate.now().plusMonths(1), "homeopathique");
            assertFalse(medicamentService.searchMedicamentsByCategory("Genre1").isEmpty());
        } catch (Exception e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }

    @Test
    public void testSearchByFirstLetters() {
        try {
            medicamentService.addMedicament("Med1", "Genre1", 100.0, 123456789, LocalDate.now().plusMonths(1), "homeopathique");
            assertFalse(medicamentService.searchMedicamentsByFirstLetters("Med").isEmpty());
        } catch (Exception e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }

    @Test

    public void testDeleteAllMedicaments() {
        try {
            medicamentService.addMedicament("Med2", "Genre2", 100.0, 143456789, LocalDate.now().plusMonths(5), "homeopathique");
            medicamentService.getMedicaments().forEach(medicament -> {
                try {
                    medicamentService.deleteMedicament(medicament.getId());
                } catch (java.sql.SQLIntegrityConstraintViolationException e) {
                    System.out.println("Could not delete medicament with ID " + medicament.getId() + " due to foreign key constraint.");
                } catch (Exception e) {
                    fail("Exception should not have been thrown: " + e.getMessage());
                }
            });
            if (!medicamentService.getMedicaments().isEmpty()) {
                System.out.println("Some medicaments could not be deleted.");
            }
        } catch (Exception e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }
}