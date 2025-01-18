package com.example.pharmacymanagement.models;

import java.time.LocalDate;

public class ClientFidele extends Client {
    private LocalDate dateAdhesion;

    public ClientFidele(String nom, String prenom, String email, String telephone, LocalDate dateAdhesion) {
        super(nom, prenom, email, telephone);
        this.dateAdhesion = dateAdhesion;
    }

    public LocalDate getDateAdhesion() {
        return dateAdhesion;
    }

    public void setDateAdhesion(LocalDate dateAdhesion) {
        this.dateAdhesion = dateAdhesion;
    }
    @Override
    public String toString() {
        return super.toString()+
                ", dateAdhesion=" + dateAdhesion +
                '}';
    }
}