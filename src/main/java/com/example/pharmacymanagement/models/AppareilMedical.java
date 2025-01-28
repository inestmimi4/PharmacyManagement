package com.example.pharmacymanagement.models;

import java.time.LocalDate;

public class AppareilMedical implements Vendable {
    private long id;
    private String nom;
    private String marque;
    private double prix;
    private String numeroSerie;
    private LocalDate dateFabrication;
    private int garantieEnMois;
    private LocalDate dateAchat;
    private boolean disponible;

    public AppareilMedical(String nom, String marque, double prix, String numeroSerie, LocalDate dateFabrication, int garantieEnMois, LocalDate dateAchat, boolean disponible) {
        this.nom = nom;
        this.marque = marque;
        this.prix = prix;
        this.numeroSerie = numeroSerie;
        this.dateFabrication = dateFabrication;
        this.garantieEnMois = garantieEnMois;
        this.dateAchat = dateAchat;
        this.disponible = disponible;
    }

    public AppareilMedical() {

    }


    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public double getPrix() {
        return prix;
    }

    @Override
    public int getId() {
        return (int)id;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public LocalDate getDateFabrication() {
        return dateFabrication;
    }

    public void setDateFabrication(LocalDate dateFabrication) {
        this.dateFabrication = dateFabrication;
    }

    public int getGarantieEnMois() {
        return garantieEnMois;
    }

    public void setGarantieEnMois(int garantieEnMois) {
        this.garantieEnMois = garantieEnMois;
    }

    public LocalDate getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }


    @Override
    public double getTranche() {
        return prix *0.9;
    }

    @Override
    public String toString() {
        return "AppareilMedical{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", marque='" + marque + '\'' +
                ", prix=" + prix +
                ", numeroSerie='" + numeroSerie + '\'' +
                ", dateFabrication=" + dateFabrication +
                ", garantieEnMois=" + garantieEnMois +
                ", dateAchat=" + dateAchat +
                ", disponible=" + disponible +
                '}';
    }


    public int getGarantie() {
        return garantieEnMois;
    }

    public LocalDate getDateMiseEnService() {
        return dateAchat;
    }
}