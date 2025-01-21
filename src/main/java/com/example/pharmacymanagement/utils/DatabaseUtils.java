// src/main/java/com/example/pharmacymanagement/utils/DatabaseUtils.java
package com.example.pharmacymanagement.utils;

import com.example.pharmacymanagement.mysql_connection.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {
    public static boolean recordExists(String tableName, String columnName, long id) throws SQLException {
        String query = String.format("SELECT COUNT(*) FROM %s WHERE %s = ?", tableName, columnName);
        try (Connection connection = MySqlConnection.getMySqlConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        }
        return false;
    }
}