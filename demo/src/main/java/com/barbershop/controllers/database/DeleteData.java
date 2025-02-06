package com.barbershop.controllers.database;

import com.barbershop.controllers.alerts.AlertController;
import com.barbershop.models.Event;
import com.barbershop.models.Invoice;

public class DeleteData {
    public static void DeleteEvent(int id) {
        GetData.GetAll();
        boolean invoice_found = false;
        for (Invoice i : GetData.AllInvoices) {
            if (i.getEventId() == id) {
                invoice_found = true;
                break;
            }
        }
        if (invoice_found) {
        	AlertController.showError("Erro ao Excluir Evento " + id, "Este evento está vinculado a uma fatura e não pode ser excluído!");
        } else {
            DB.deleteRow("db_barbershop", "Event", "event_id = '" + id + "'");
        }
        GetData.GetAll();
    }

    public static void DeleteInvoice(int id) {
        DB.deleteRow("db_barbershop", "BillProduct", "invoice_id = '" + id + "'");
        DB.deleteRow("db_barbershop", "BillService", "invoice_id = '" + id + "'");
        DB.deleteRow("db_barbershop", "Invoice", "invoice_id = '" + id + "'");
        GetData.GetAll();
    }

    public static void DeleteProduct(int id) {
        GetData.GetAll();
        boolean found = false;
        for (Invoice i : GetData.AllInvoices) {
            if (i.getEventId() == id) {
                found = true;
                break;
            }
        }
        if (found) {
        	AlertController.showError("Erro ao Excluir Produto " + id, "Este produto está vinculado a uma fatura e não pode ser excluído!");
        } else {
            DB.deleteRow("db_barbershop", "Product", "product_id = '" + id + "'");
        }
        GetData.GetAll();
    }

    public static void DeleteService(int id) {
        GetData.GetAll();
        boolean found = false;
        for (Invoice i : GetData.AllInvoices) {
            if (i.getEventId() == id) {
                found = true;
                break;
            }
        }
        for (Event e : GetData.AllEvents) {
            if (e.getServiceId() == id) {
                found = true;
                break;
            }
        }
        if (found) {
        	AlertController.showError("Erro ao Excluir Serviço " + id, "Este serviço está vinculado a uma fatura e não pode ser excluído!");
        } else {
            DB.deleteRow("db_barbershop", "Service", "service_id = '" + id + "'");
        }
        GetData.GetAll();
    }

    public static void DeleteClient(int id) {
        boolean found = false;
        for (Event e : GetData.AllEvents) {
            if (e.getClientId() == id) {
                found = true;
                break;
            }
        }
        if (found) {
        	AlertController.showError("Erro ao Excluir Cliente " + id, "Este cliente possui um(s) evento(s) associado(s) e não pode ser excluído!");
        } else {
            DB.deleteRow("db_barbershop", "Client", "client_id = '" + id + "'");
        }
        GetData.GetAll();
    }
}
