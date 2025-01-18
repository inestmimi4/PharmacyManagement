package com.example.pharmacymanagement.models;

import java.time.LocalDate;

class MedicamentHomeopathique extends Medicament {
    private String plante;

    public MedicamentHomeopathique(String plante, String nom, String genre, double prix, Long numeroSerie, LocalDate dateExpiration) throws NegativPrixMedicament, DateExpirationDepasseException {
        super(nom, genre, prix, numeroSerie, dateExpiration);
        this.plante = plante;
    }

    public String getPlante() {
        return plante;
    }

    public void setPlante(String plante) {
        this.plante = plante;
    }

    @Override
    public String toString() {
        return super.toString() + ", plantesUtilisees=" + plante;
    }

    @Override
    public double getTranche() {
        return super.getPrix() * 0.8;
    }
}