package com.example.pharmacymanagement.Test;

import com.example.pharmacymanagement.services.AppareilMedicalService;

import java.time.LocalDate;

public class TestAppareilMedicalService {

        public static void main(String[] args) {
            try {
                AppareilMedicalService appareilMedicalService = new AppareilMedicalService();
                appareilMedicalService.addAppareilMedical("Appareil2", "Type1", 200.0, "9876544321", LocalDate.now().plusYears(1), 24, LocalDate.now(), true);
                System.out.println("Appareil medical added successfully!");
                System.out.println("List of appareils medicals:");
                appareilMedicalService.getAppareilsMedicals().forEach(System.out::println);
                appareilMedicalService.updateAppareilMedical(1, "Appareil1 Updated", "Type1", 250.0, "987654321", LocalDate.now().plusYears(2), 36, LocalDate.now(), true);
                System.out.println("Appareil medical updated successfully!");

                System.out.println("List of appareils medicals after update:");
                appareilMedicalService.getAppareilsMedicals().forEach(System.out::println);


                System.out.println("Delete all appareils medicals");
                appareilMedicalService.getAppareilsMedicals().forEach(appareil -> {
                    try {
                        appareilMedicalService.deleteAppareilMedical(appareil.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


