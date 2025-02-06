package com.barbershop.models;

import java.time.LocalDateTime;

import com.barbershop.controllers.database.GetData;
import com.barbershop.controllers.patterns.PaternController;

public class Event {
    private int event_id;
    private LocalDateTime date_time;
    private int client_id;
    private Client client;
    private int invoice_id;
    private Invoice invoice;
    private int service_id;
    private Service service;
    private String decription;
    private int type; //1: not done, 0: canceled, 2: done

    public Event() {
    }

    //get from database
    public Event(int event_id, LocalDateTime date_time, int client_id, int invoice_id, int service_id,
                 String decription, int type) {
        this.event_id = event_id;
        this.date_time = date_time;
        this.client_id = client_id;
        this.invoice_id = invoice_id;
        this.service_id = service_id;
        this.decription = decription;
        this.type = type;
        for (Client c : GetData.AllClients) {
            if (c.getClient_id() == client_id) {
                this.client = c;
                break;
            }
        }
        for (Invoice i : GetData.AllInvoices) {
            if (i.getInvoice_id() == invoice_id) {
                this.invoice = i;
                break;
            }
        }
        for (Service s : GetData.AllServices) {
            if (s.getServiceId() == service_id) {
                this.service = s;
            }
        }
    }

    //insert in database
    public Event(LocalDateTime date_time, int client_id, int invoice_id, int service_id, String decription,
                 int type) {
        this.date_time = date_time;
        this.client_id = client_id;
        this.invoice_id = invoice_id;
        this.service_id = service_id;
        this.decription = decription;
        this.type = type;
        for (Client c : GetData.AllClients) {
            if (c.getClient_id() == client_id) {
                this.client = c;
                break;
            }
        }
        for (Invoice i : GetData.AllInvoices) {
            if (i.getInvoice_id() == invoice_id) {
                this.invoice = i;
                break;
            }
        }
        for (Service s : GetData.AllServices) {
            if (s.getServiceId() == service_id) {
                this.service = s;
            }
        }
    }

    public Event(LocalDateTime date_time, int client_id, int invoice_id, int service_id, String decription) {
        this.date_time = date_time;
        this.client_id = client_id;
        this.invoice_id = invoice_id;
        this.service_id = service_id;
        this.decription = decription;
        this.type = 1;
    }

    public int getEventId() {
        return event_id;
    }

    public void setEventId(int event_id) {
        this.event_id = event_id;
    }

    public LocalDateTime getDateTime() {
        return date_time;
    }

    public void setDateTime(LocalDateTime date_time) {
        this.date_time = date_time;
    }

    public String getDate() {
        return this.date_time.getDayOfMonth() + " " + this.date_time.getMonth() + " " + this.date_time.getYear();
    }

    public String getTime() {
        int min = this.date_time.getMinute();
        int hour = this.date_time.getHour();
        if (min < 10) {
            if (hour < 10) {
                return this.date_time.getHour() + "0" + ":" + min + "0";
            }
            return this.date_time.getHour() + ":" + min + "0";
        }
        return this.date_time.getHour() + ":" + min;
    }

    public String getDateAndTime() {
        return getDate() + " " + getTime();
    }

    public int getClientId() {
        return client_id;
    }

    public void setClientId(int client_id) {
        this.client_id = client_id;
    }

    public int getInvoiceId() {
        return invoice_id;
    }

    public void setInvoiceId(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public int getServiceId() {
        return service_id;
    }

    public void setServiceId(int service_id) {
        this.service_id = service_id;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return this.getDate() + "\n" + this.getTime() + "\n" + PaternController.capitalize(this.getClient().getFirst_name()) + " " + this.getClient().getLast_name().toUpperCase() + "\n" + this.getDecription() + "\n" + this.getService().getDescription();
    }
/*  public String toString() {
        return "Event [event_id=" + event_id + ", date_time=" + date_time + ", client_id=" + client_id + ", invoice_id="
                + invoice_id + ", service_id=" + service_id + ", decription=" + decription + ", type=" + type + "]";
    } */
}
