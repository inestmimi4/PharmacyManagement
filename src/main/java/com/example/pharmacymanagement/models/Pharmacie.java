package com.example.pharmacymanagement.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Pharmacie {
    private ArrayList<Medicament> listeMedicaments;
    private ArrayList<ClientFidele> listeClientsFideles;
    private HashMap<Long, Integer> mapMedicaments;
    private HashMap<Long, Double> mapClientsFideles;
    static ArrayList<Vendable> listeVendables = new ArrayList<>();

    public Pharmacie() {
        this.listeMedicaments = new ArrayList<>();
        this.listeClientsFideles = new ArrayList<>();
        this.mapMedicaments = new HashMap<>();
        this.mapClientsFideles = new HashMap<>();
    }

    public ArrayList<Medicament> getListeMedicaments() {
        return listeMedicaments;
    }

    public void setListeMedicaments(ArrayList<Medicament> listeMedicaments) {
        this.listeMedicaments = listeMedicaments;
    }

    public ArrayList<ClientFidele> getListeClientsFideles() {
        return listeClientsFideles;
    }

    public void setListeClientsFideles(ArrayList<ClientFidele> listeClientsFideles) {
        this.listeClientsFideles = listeClientsFideles;
    }

    public HashMap<Long, Integer> getMapMedicaments() {
        return mapMedicaments;
    }

    public void setMapMedicaments(HashMap<Long, Integer> mapMedicaments) {
        this.mapMedicaments = mapMedicaments;
    }

    public HashMap<Long, Double> getMapClientsFideles() {
        return mapClientsFideles;
    }

    public void setMapClientsFideles(HashMap<Long, Double> mapClientsFideles) {
        this.mapClientsFideles = mapClientsFideles;
    }

    public void ajouterMedicament(Medicament m) {
        listeMedicaments.add(m);
        mapMedicaments.put(m.getNumeroSerie(), mapMedicaments.getOrDefault(m.getNumeroSerie(), 0) + 1);
    }

    public void supprimerMedicament(String nomMedicament) {
        listeMedicaments.removeIf(m -> {
            if (m.getNom().equals(nomMedicament)) {
                mapMedicaments.put(m.getNumeroSerie(), mapMedicaments.get(m.getNumeroSerie()) - 1);
                if (mapMedicaments.get(m.getNumeroSerie()) == 0) {
                    mapMedicaments.remove(m.getNumeroSerie());
                }
                return true;
            }
            return false;
        });
    }

    public int nombreMedicaments() {
        return listeMedicaments.size();
    }

    public double achatMedicament(String nomMedicament) {
        for (Medicament m : listeMedicaments) {
            if (m.getNom().equals(nomMedicament)) {
                long numeroSerie = m.getNumeroSerie();
                mapMedicaments.put(numeroSerie, mapMedicaments.get(numeroSerie) - 1);
                if (mapMedicaments.get(numeroSerie) == 0) {
                    mapMedicaments.remove(numeroSerie);
                }
                listeMedicaments.remove(m);
                return m.getPrix();
            }
        }
        return 0.0;
    }

    public double achatMedicament(String nomMedicament, long cin) {
        double prix = achatMedicament(nomMedicament);
        mapClientsFideles.put(cin, mapClientsFideles.getOrDefault(cin, 0.0) + prix);
        if (mapClientsFideles.get(cin) > 100) {
            prix *= 0.85;
            mapClientsFideles.put(cin, prix);
        }
        return prix;
    }

    public void ajouterVendable(Vendable vendable) {
        listeVendables.add(vendable);
    }

    public void listerMedicamentsHomeo(ArrayList<Vendable> vendables) {
        vendables.stream()
                .filter(v -> v instanceof MedicamentHomeopathique)
                .forEach(System.out::println);
    }

    public double calculerPrixFidele(ArrayList<Vendable> vendables, int credit) throws CreditDepasse {
        if (credit > 20) {
            throw new CreditDepasse("Vous avez juste 20 dt à créditer !");
        }
        double total = vendables.stream().mapToDouble(Vendable::getTranche).sum();
        return total - credit;
    }

    public ArrayList<Vendable> listerMedicamentsRisques() {
        LocalDate deuxMoisPlusTard = LocalDate.now().plusMonths(2);
        return listeVendables.stream()
                .filter(v -> v instanceof Medicament)
                .map(v -> (Medicament) v)
                .filter(m -> m.getDateExpiration().isBefore(deuxMoisPlusTard))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public long compteMedicament() {
        return listeMedicaments.stream()
                .filter(m -> m.getNom().startsWith("p"))
                .filter(m -> m.getPrix() > 2)
                .count();
    }

    public void trierClientsOrdreAlphabetique() {
        listeClientsFideles = listeClientsFideles.stream()
                .sorted(Comparator.comparing(ClientFidele::getNom))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}