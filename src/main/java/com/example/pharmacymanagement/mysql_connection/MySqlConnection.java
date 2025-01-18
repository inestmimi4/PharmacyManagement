package com.example.pharmacymanagement.mysql_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {

    public static Connection getMySqlConnection() throws SQLException {
        String url = "jdbc:mysql://localhost/bibliotheque";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: unable to load driver class!");
            throw new SQLException("MySQL JDBC Driver not found", e);
        }

        return DriverManager.getConnection(url, username, password);
    }
}