package com.barbershop.models;

import java.util.ArrayList;
import java.util.List;

import com.barbershop.controllers.database.GetData;

public class Invoice {
    private int invoice_id = 0;
    private int client_id = 0;
    private Client client;
    private int event_id = 0;
    private Event event;
    private List<Integer> products_ids = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Integer> services_ids = new ArrayList<>();
    private List<Service> services = new ArrayList<>();
    private double sub_total = 0.0;
    private double reductions = 0.0;
    private double tax = 0.0;
    private double total = 0.0;

    public Invoice() {
    }

    //get from database
    public Invoice(int invoice_id, int client_id, int event_id, List<Integer> products_ids, List<Integer> services_ids,
                   double sub_total, double reductions, double tax, double total) {
        this.invoice_id = invoice_id;
        this.client_id = client_id;
        this.event_id = event_id;
        this.products_ids = products_ids;
        this.services_ids = services_ids;
        this.sub_total = sub_total;
        this.reductions = reductions;
        this.tax = tax;
        this.total = total;
        for (Product p : GetData.AllProducts) {
            for (Integer i : products_ids) {
                if (p.getProductId() == i) {
                    this.products.add(p);
                }
            }
        }
        for (Service s : GetData.AllServices) {
            for (Integer i : services_ids) {
                if (s.getServiceId() == i) {
                    this.services.add(s);
                }
            }
        }
        for (Event e : GetData.AllEvents) {
            if (e.getEventId() == this.event_id) {
                this.event = e;
                break;
            }
        }
        for (Client c : GetData.AllClients) {
            if (c.getClient_id() == client_id) {
                this.client = c;
                break;
            }
        }
    }

    //insert in database
    public Invoice(int client_id, int event_id, List<Integer> products_ids, List<Integer> services_ids,
                   double sub_total, double reductions, double tax, double total) {
        this.client_id = client_id;
        this.event_id = event_id;
        this.products_ids = products_ids;
        this.services_ids = services_ids;
        this.sub_total = sub_total;
        this.reductions = reductions;
        this.tax = tax;
        this.total = total;
    }

    public Invoice(int client_id, int event_id, List<Integer> products_ids, List<Integer> services_ids, double reductions) {
        this.client_id = client_id;
        this.event_id = event_id;
        this.products_ids = products_ids;
        this.services_ids = services_ids;
        for (Product p : GetData.AllProducts) {
            for (Integer i : products_ids) {
                if (p.getProductId() == i) {
                    this.products.add(p);
                }
            }
        }
        for (Service s : GetData.AllServices) {
            for (Integer i : services_ids) {
                if (s.getServiceId() == i) {
                    this.services.add(s);
                }
            }
        }
        //calculate the bill
        for (Product p : products) {
            this.sub_total += p.getPrice();
        }
        for (Service s : services) {
            this.sub_total += s.getPrice();
        }
        this.tax = sub_total * 0.5;
        this.reductions = reductions;
        this.total = sub_total + this.tax - reductions;
        for (Event e : GetData.AllEvents) {
            if (e.getEventId() == this.event_id) {
                this.event = e;
                break;
            }
        }
        for (Client c : GetData.AllClients) {
            if (c.getClient_id() == client_id) {
                this.client = c;
                break;
            }
        }
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public int getClientId() {
        return client_id;
    }

    public void setClientId(int client_id) {
        this.client_id = client_id;
    }

    public int getEventId() {
        return event_id;
    }

    public void setEventId(int event_id) {
        this.event_id = event_id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public double getSub_total() {
        return sub_total;
    }

    public void setSub_total(double sub_total) {
        this.sub_total = sub_total;
    }

    public double getReductions() {
        return reductions;
    }

    public void setReductions(double reductions) {
        this.reductions = reductions;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Integer> getProducts_ids() {
        return products_ids;
    }

    public void setProducts_ids(List<Integer> products_ids) {
        this.products_ids = products_ids;
    }

    public List<Integer> getServices_ids() {
        return services_ids;
    }

    public void setServices_ids(List<Integer> services_ids) {
        this.services_ids = services_ids;
    }

    @Override
    public String toString() {
        return "\n>>\nInvoice [\ninvoice_id=" + invoice_id + ", \nclient_id=" + client_id + ", \nevent_id=" + event_id
                + ", \nproducts=" + products + ", \nservices=" + services + ", \nsub_total=" + sub_total + ", \nreductions="
                + reductions + ", \ntax=" + tax + ", \ntotal=" + total + "]\n<<";
    }
}
