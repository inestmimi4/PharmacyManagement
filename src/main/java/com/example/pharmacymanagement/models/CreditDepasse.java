package com.example.pharmacymanagement.models;

public class CreditDepasse extends Exception {
    public CreditDepasse(String message) {
        super(message);
    }
}