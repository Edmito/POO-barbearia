package com.barbershop.models;

public class InvoiceProduct {
    private Product product;
    private int quantity;
    private Double price;

    public InvoiceProduct(Product product) {
        this.product = product;
        this.quantity = 1;
        this.price = product.getPrice() * quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.price = this.product.getPrice() * quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return product.getName() + "               " + price + " DHs"
                + "\n" + product.getPrice() + "              x" + quantity;
    }
}
