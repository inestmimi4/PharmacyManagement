package com.example.pharmacymanagement.models;

import java.time.LocalDate;

class MedicamentChimique extends Medicament {
    private String constituantsChimiques;
    private int ageMinimum;

    public MedicamentChimique(String constituantsChimiques, int ageMinimum, String nom, String genre, double prix, Long numeroSerie, LocalDate dateExpiration) throws NegativPrixMedicament, DateExpirationDepasseException {
        super(nom, genre, prix, numeroSerie, dateExpiration);
        this.constituantsChimiques = constituantsChimiques;
        this.ageMinimum = ageMinimum;
    }

    public String getConstituantsChimiques() {
        return constituantsChimiques;
    }

    public void setConstituantsChimiques(String constituantsChimiques) {
        this.constituantsChimiques = constituantsChimiques;
    }

    public int getAgeMinimum() {
        return ageMinimum;
    }

    public void setAgeMinimum(int ageMinimum) {
        this.ageMinimum = ageMinimum;
    }

    @Override
    public String toString() {
        return super.toString() + ", constituantsChimiques=" + constituantsChimiques + ", ageMinimum=" + ageMinimum;
    }

    @Override
    public double getTranche() {
        return super.getPrix() * 0.9;
    }
}