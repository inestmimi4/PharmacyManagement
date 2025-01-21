package com.example.pharmacymanagement.models;

import java.time.LocalDate;

public class VentesAppareils {
    private long id;
    private long clientId;
    private long appareilId;
    private int quantity;
    private double totalPrice;
    private LocalDate date;
    private String paymentMode;

    public VentesAppareils() {
    }

    public VentesAppareils(long clientId, long appareilId, int quantity, double totalPrice, LocalDate date, String paymentMode) {
        this.clientId = clientId;
        this.appareilId = appareilId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.date = date;
        this.paymentMode = paymentMode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getAppareilId() {
        return appareilId;
    }

    public void setAppareilId(long appareilId) {
        this.appareilId = appareilId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    @Override
    public String toString() {
        return "VentesAppareils{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", appareilId=" + appareilId +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", date=" + date +
                ", paymentMode='" + paymentMode + '\'' +
                '}';
    }
}