package com.example.pharmacymanagement.Test;

import com.example.pharmacymanagement.services.ClientService;

import java.time.LocalDate;


public class clientTest {

    public static void main(String[] args) {
        try {
            ClientService clientService = new ClientService();
            clientService.addClient("John Doe", "john.doe@example.com", "1234567890", "123 Main St", LocalDate.of(1990, 1, 1));
            System.out.println("Client added successfully!");
            System.out.println("List of clients:");
            clientService.getClients().forEach(System.out::println);
            clientService.updateClient(1, "John Doe Updated", "john.doe.updated@example.com", "0987654321", "456 Elm St", LocalDate.of(1990, 1, 1));
            System.out.println("Client updated successfully!");
            System.out.println("List of clients after update:");
            clientService.getClients().forEach(System.out::println);
            System.out.println("Delete all clients");
            clientService.getClients().forEach(client -> {
                try {
                    clientService.deleteClient(client.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
