package com.example.pharmacymanagement.Test;

import com.example.pharmacymanagement.services.MedicamentService;

import java.time.LocalDate;

public class medicamentServicetest {
    public static void main(String[] args) {
        try {
            MedicamentService medicamentService = new MedicamentService();

            medicamentService.addMedicament("Med1", "Genre1", 100.0, 123456789, LocalDate.now().plusMonths(1), "chimique");
            System.out.println("Medicament added successfully!");
            System.out.println("List of medicaments:");
            medicamentService.getMedicaments().forEach(System.out::println);
            medicamentService.updateMedicament(1, "Med1 Updated", "Genre1", 150.0, 123456789, LocalDate.now().plusMonths(2),"chimique");
            System.out.println("Medicament updated successfully!");
            System.out.println("List of medicaments after update:");
            medicamentService.getMedicaments().forEach(System.out::println);

            System.out.println("Search by name 'Med1':");
            medicamentService.searchMedicamentsByName("Med1").forEach(System.out::println);
            System.out.println("Search by category 'Genre1':");
            medicamentService.searchMedicamentsByCategory("Genre1").forEach(System.out::println);

            System.out.println("Search by first letters 'Med':");
            medicamentService.searchMedicamentsByFirstLetters("Med").forEach(System.out::println);

            System.out.println("Delete all medicaments");
            medicamentService.getMedicaments().forEach(medicament -> {
                try {
                    medicamentService.deleteMedicament(medicament.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
