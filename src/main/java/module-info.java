module com.example.pharmacymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.example.pharmacymanagement to javafx.fxml;
    exports com.example.pharmacymanagement;

    opens com.example.pharmacymanagement.controllers to javafx.fxml;

    requires javafx.graphics;
    requires junit;

    exports com.example.pharmacymanagement.Test to junit;
    opens com.example.pharmacymanagement.views to javafx.fxml;
    opens com.example.pharmacymanagement.models to javafx.base;
    exports com.example.pharmacymanagement.views;
    exports com.example.pharmacymanagement.controllers;
    exports com.example.pharmacymanagement.models;
    exports com.example.pharmacymanagement.services;
    opens com.example.pharmacymanagement.services to javafx.base;



}