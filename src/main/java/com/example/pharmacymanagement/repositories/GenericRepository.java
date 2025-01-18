package com.example.pharmacymanagement.repositories;

import java.sql.SQLException;
import java.util.List;

public interface GenericRepository<T> {
    void add(T entity) throws SQLException;
    List<T> getAll() throws SQLException;
    void update(T entity) throws SQLException;
    void delete(int id) throws SQLException;
}
