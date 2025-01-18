package com.example.pharmacymanagement.mysql_connection;

import java.sql.Connection;
import java.sql.SQLException;

public class MySqlConnectionTest {
    public static void main(String[] args) {
        try (Connection connection = MySqlConnection.getMySqlConnection()) {
            if (connection != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }
}