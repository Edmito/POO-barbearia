package com.barbershop.models;

public class Service {
    private int service_id;
    private String name;
    private String description;
    private double price;

    public Service() {
    }

    //get from database
    public Service(int service_id, String name, String description, double price) {
        this.service_id = service_id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    //insert in database
    public Service(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getServiceId() {
        return service_id;
    }

    public void setServiceId(int service_id) {
        this.service_id = service_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nDescription: " + description + "\nPrice: " + price;
    }
/*     public String toString() {
        return "Service [service_id=" + service_id + ", name=" + name + ", description=" + description + ", price=" + price + "]";
    } */
}
