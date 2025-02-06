package com.barbershop.models;

public class Product {
    private int product_id;
    private String name;
    private String description;
    private int quantity;
    private double price;

    public Product() {
    }

    //get from database
    public Product(int product_id, String name, String description, int quantity, double price) {
        this.product_id = product_id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    //insert in database
    public Product(String name, String description, int quantity, double price) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProductId() {
        return product_id;
    }

    public void setProductId(int product_id) {
        this.product_id = product_id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nDescription: " + description + "\nPrice: " + price + "\nQuantity: " + quantity;
    }
/*     public String toString() {
        return "Product [product_id=" + product_id + ", name=" + name + ", description=" + description + ", quantity="
                + quantity + ", price=" + price + "]";
    } */
}
