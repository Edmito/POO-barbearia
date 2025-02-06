package com.barbershop.models;

public class InvoiceService {
    private Service service;

    public InvoiceService(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return service.getName() + "               " + service.getPrice() + " DHs";
    }
}
