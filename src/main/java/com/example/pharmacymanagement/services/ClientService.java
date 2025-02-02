package com.example.pharmacymanagement.services;

import com.example.pharmacymanagement.models.ClientFidele;
import com.example.pharmacymanagement.repositories.ClientRepository;

import java.time.LocalDate;
import java.util.List;

public class ClientService {

    private final ClientRepository clientRepository = new ClientRepository();

    public void addClient(String nom, String prenom, String email, String telephone, LocalDate dateAdhesion) throws Exception {
        ClientFidele client = new ClientFidele(nom, prenom, email, telephone, dateAdhesion);
        clientRepository.add(client);
    }

    public List<ClientFidele> getClients() throws Exception {
        return clientRepository.getAll();
    }

    public void updateClient(int id, String nom, String prenom, String email, String telephone, LocalDate dateAdhesion) throws Exception {
        ClientFidele client = new ClientFidele(nom, prenom, email, telephone, dateAdhesion);
        client.setId(id);
        clientRepository.update(client);
    }

    public void deleteClient(int id) throws Exception {
        clientRepository.delete(id);
    }

    public int getClientIdByEmail(String mail) {
        return clientRepository.getIdByEmail(mail);
    }

}