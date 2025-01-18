package com.example.pharmacymanagement.Test;

import com.example.pharmacymanagement.services.ClientService;
import org.junit.Test;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.fail;

public class TestClientService {

    private static final Logger logger = Logger.getLogger(TestClientService.class.getName());

    @Test
    public void testDeleteAllClients() {
        ClientService clientService = new ClientService();
        try {
            clientService.getClients().forEach(client -> {
                try {
                    clientService.deleteClient(client.getId());
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error deleting client with ID: " + client.getId(), e);
                }
            });
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving clients", e);
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testAddClient() {
        ClientService clientService = new ClientService();
        try {
            clientService.addClient("ines", "ines@example.com", "ines@", "123 Main St", LocalDate.of(1990, 1, 1));
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            logger.log(Level.WARNING, "Duplicate entry for email: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding client", e);
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testUpdateClient() {
        ClientService clientService = new ClientService();
        try {
            clientService.addClient("John Doe", "john.doe@example.com", "1234567870", "123 Main St", LocalDate.of(1990, 1, 1));
            clientService.updateClient(1, "John Doe Updated", "john.doe.updated@example.com", "0987654321", "456 Elm St", LocalDate.of(1990, 1, 1));
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            logger.log(Level.WARNING, "Duplicate entry for email: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating client", e);
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testDeleteClient() {
        ClientService clientService = new ClientService();
        try {
            clientService.addClient("John Doe", "john.doe@example.com", "1234567790", "123 Main St", LocalDate.of(1990, 1, 1));
            clientService.deleteClient(1);
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            logger.log(Level.WARNING, "Duplicate entry for email: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting client", e);
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testGetClients() {
        ClientService clientService = new ClientService();
        try {
            clientService.addClient("John Doe", "john.doe@example.com", "1234577890", "123 Main St", LocalDate.of(1990, 1, 1));
            clientService.getClients().forEach(System.out::println);
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            logger.log(Level.WARNING, "Duplicate entry for email: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving clients", e);
            fail("Exception should not have been thrown");
        }
    }

    public static void main(String[] args) {
        TestClientService testClientService = new TestClientService();
        testClientService.testDeleteAllClients();
        testClientService.testAddClient();
        testClientService.testUpdateClient();
        testClientService.testDeleteClient();
        testClientService.testGetClients();
    }
}