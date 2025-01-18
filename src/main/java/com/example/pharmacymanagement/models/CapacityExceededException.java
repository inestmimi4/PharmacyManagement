package com.example.pharmacymanagement.models;

public class CapacityExceededException extends Exception {
    public CapacityExceededException(String message) {
        super(message);
    }
}
