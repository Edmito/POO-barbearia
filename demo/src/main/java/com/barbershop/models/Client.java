package com.barbershop.models;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private int client_id;
    private String first_name;
    private String last_name;
    private int phone;
    private List<Event> appointments = new ArrayList<>();
    private List<Invoice> invoices = new ArrayList<>();

    public Client() {
    }

    //get from database
    public Client(int client_id, String first_name, String last_name, int phone, List<Event> appointments, List<Invoice> invoices) {
        this.client_id = client_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.appointments = appointments;
        this.invoices = invoices;
    }

    //insert in database
    public Client(String first_name, String last_name, int phone, List<Event> appointments, List<Invoice> invoices) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.appointments = new ArrayList<>();
        this.invoices = new ArrayList<>();
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public List<Event> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Event> appointments) {
        this.appointments = appointments;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public String toString() {
        return "Name: " + first_name + " " + last_name + "\nPhone: " + phone + "\nInvoice(s): " + invoices.size() + "\nAppointment(s): " + appointments.size();
    }
/*     public String toString() {
        return "Client [client_id=" + client_id + ", first_name=" + first_name + ", last_name=" + last_name + ", phone="
                + phone + ", appointments=" + appointments + ", invoices=" + invoices + "]";
    } */
}
