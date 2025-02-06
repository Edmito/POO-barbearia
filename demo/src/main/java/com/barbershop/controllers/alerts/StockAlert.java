package com.barbershop.controllers.alerts;

public class StockAlert extends Exception {
    public StockAlert(String msg) {
        super(msg);
    }
}
