package com.example.pharmacymanagement.models;

import java.util.ArrayList;
import java.util.List;

public class Etagere {
    private int capacite;
    private List<Medicament> medicaments;

    public Etagere(int nombreMax) {
        this.capacite = nombreMax;
        this.medicaments = new ArrayList<>(nombreMax);
    }

    public int nombreMedicaments() {
        return medicaments.size();
    }

    public void ajouter(Medicament m) throws CapacityExceededException {
        if (nombreMedicaments() >= capacite) {
            throw new CapacityExceededException("Vous avez dépassé le nombre maximal de médicaments.");
        }
        medicaments.add(m);
    }

    public Medicament getMedicament(int position) throws InvalidPositionException {
        if (position <= 0 || position > nombreMedicaments()) {
            throw new InvalidPositionException("La position est incorrecte.");
        }
        return medicaments.get(position - 1);
    }

    public int chercher(String nom, String genre) {
        for (int i = 0; i < medicaments.size(); i++) {
            Medicament m = medicaments.get(i);
            if (m.getNom().equals(nom) && m.getGenre().equals(genre)) {
                return i + 1;
            }
        }
        return 0;
    }

    public Medicament enleverMedicament(int position) throws InvalidPositionException {
        if (position <= 0 || position > nombreMedicaments()) {
            throw new InvalidPositionException("La position est incorrecte.");
        }
        return medicaments.remove(position - 1);
    }

    public Medicament enleverMedicament(String nom, String genre) throws MedicamentNotFoundException, InvalidPositionException {
        int position = chercher(nom, genre);
        if (position == 0) {
            throw new MedicamentNotFoundException("Médicament non trouvé.");
        }
        return enleverMedicament(position);
    }

    @Override
    public String toString() {
        return "Etagere [capacite=" + capacite + ", medicaments=" + medicaments + ", nombreMedicaments=" + nombreMedicaments() + "]";
    }
}