package com.example.pharmacymanagement.models;

import java.time.LocalDate;
import java.util.Scanner;

public class Medicament implements Vendable {
    private static long n;
    private long code;
    private String nom;
    private String genre;
    private double prix;
    private Long numeroSerie;
    private LocalDate dateExpiration;
    private String typeMedicament;


    public Medicament() {
        this.nom = "";
        this.genre = "";
        this.prix = 0.0;
        n++;
        this.code = n;
    }
    public Medicament(String nom, String genre, double prix, Long numeroSerie, LocalDate dateExpiration, String typeMedicament) throws NegativPrixMedicament, DateExpirationDepasseException {
        if (prix < 0) {
            throw new NegativPrixMedicament("Le prix du médicament doit être positif.");
        }
        if (dateExpiration.isBefore(LocalDate.now())) {
            throw new DateExpirationDepasseException("La date d'expiration est dépassée.");
        }
        this.nom = nom;
        this.genre = genre;
        this.prix = prix;
        this.numeroSerie = numeroSerie;
        this.dateExpiration = dateExpiration;
        this.typeMedicament = typeMedicament;
        n++;
        this.code = n;
    }
    public String getTypeMedicament() {
        return typeMedicament;
    }
    public void setTypeMedicament(String typeMedicament) {
        this.typeMedicament = typeMedicament;
    }


    public Medicament(String nom, String genre, double prix, Long numeroSerie, LocalDate dateExpiration) throws NegativPrixMedicament, DateExpirationDepasseException {
        if (prix < 0) {
            throw new NegativPrixMedicament("Le prix du médicament doit être positif.");
        }
        if (dateExpiration.isBefore(LocalDate.now())) {
            throw new DateExpirationDepasseException("La date d'expiration est dépassée.");
        }
        this.nom = nom;
        this.genre = genre;
        this.prix = prix;
        this.numeroSerie = numeroSerie;
        this.dateExpiration = dateExpiration;
        n++;
        this.code = n;
    }

    public Medicament(String nom, String genre, double prix, Long numeroSerie) {
        this.nom = nom;
        this.genre = genre;
        this.prix = prix;
        this.numeroSerie = numeroSerie;
        n++;
        this.code = n;
    }
    public long getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Long getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(Long numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    @Override
    public String toString() {
        return "Medicament [code=" + code + ", nom=" + nom + ", genre=" + genre + ", prix=" + prix + ", numeroSerie=" + numeroSerie + "]";
    }

    public int compareNom(Medicament m) {
        return this.nom.compareTo(m.getNom());
    }

    public int comparePrix(Medicament m) {
        return Double.compare(this.prix, m.getPrix());
    }

    public static Medicament[] remplirTab(Medicament[] tab, int n) {
        try (Scanner sc = new Scanner(System.in)) {
            for (int i = 0; i < n; i++) {
                System.out.println("Entrer le nom du médicament : ");
                String nom1 = sc.nextLine();
                System.out.println("Entrer le genre du médicament : ");
                String genre1 = sc.nextLine();
                System.out.println("Entrer le prix du médicament : ");
                double prix1 = sc.nextDouble();
                System.out.println("Entrer le numéro de série du médicament : ");
                Long numeroSerie1 = sc.nextLong();
                sc.nextLine();
                tab[i] = new Medicament(nom1, genre1, prix1, numeroSerie1);
            }
        }
        return tab;
    }

    public static void afficherNomsCommencantParF(Medicament[] tab) {
        System.out.println("\nMédicaments dont le nom commence par 'F' : ");
        for (Medicament m : tab) {
            if (m.getNom().startsWith("F")) {
                System.out.println(m);
            }
        }
    }

    @Override
    public double getTranche() {
        return 0;
    }

    public void setId(int id) {
        this.code=id ;
    }
@Override
    public int getId() {
        return (int)code;
    }
}