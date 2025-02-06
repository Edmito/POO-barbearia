package com.barbershop.controllers.database;

import com.barbershop.controllers.patterns.PaternController;
import com.barbershop.models.Client;
import com.barbershop.models.Event;
import com.barbershop.models.Invoice;
import com.barbershop.models.Product;
import com.barbershop.models.Service;

public class UpdateData {
    public static void UpdateEvent(Event event) {
        DB.updateRow("db_barbershop", "Event", "date_time = '" + event.getDateTime() + "', client_id = '" + event.getClientId() + "', invoice_id = '" + event.getInvoiceId() + "', service_id = '" + event.getServiceId() + "', description = '" + event.getDecription() + "', type = '" + event.getType() + "'", "event_id = '" + event.getEventId() + "'");
        GetData.GetAll();
    }

    public static void UpdateInvoice(Invoice invoice) {
        DB.updateRow("db_barbershop", "Invoice", "client_id = '" + invoice.getClientId() + "', appointment_id = '" + invoice.getEventId() + "', sub_total = '" + invoice.getSub_total() + "', reductions = '" + invoice.getReductions() + "', tax = '" + invoice.getTax() + "', total = '" + invoice.getTotal() + "'", "invoice_id = '" + invoice.getInvoice_id() + "'");
        DB.deleteRow("db_barbershop", "BillProduct", "invoice_id = '" + invoice.getInvoice_id() + "'");
        DB.deleteRow("db_barbershop", "BillService", "invoice_id = '" + invoice.getInvoice_id() + "'");
        for (Product p : invoice.getProducts()) {
            DB.insertRow("db_barbershop", "BillProduct (invoice_id, product_id)", "('" + invoice.getInvoice_id() + "','" + p.getProductId() + "')");
        }
        for (Service s : invoice.getServices()) {
            DB.insertRow("db_barbershop", "BillService (invoice_id, service_id)", "('" + invoice.getInvoice_id() + "','" + s.getServiceId() + "')");
        }
        GetData.GetAll();
    }

    public static void UpdateProduct(Product product) {
        DB.updateRow("db_barbershop", "Product", "name = '" + product.getName() + "', description = '" + product.getDescription() + "', quantity = '" + product.getQuantity() + "', price = '" + product.getPrice() + "'", "product_id = '" + product.getProductId() + "'");
        GetData.GetAll();
    }

    public static void UpdateService(Service service) {
        DB.updateRow("db_barbershop", "Service", "name = '" + service.getName() + "', description = '" + service.getDescription() + "', price = '" + service.getPrice() + "'", "service_id = '" + service.getServiceId() + "'");
        GetData.GetAll();
    }

    public static void UpdateClient(Client client) {
        String capitalizedFirstName = PaternController.capitalize(client.getFirst_name());

        DB.updateRow("db_barbershop", "Client", "first_name = '" + capitalizedFirstName + "', last_name = '" + client.getLast_name().toUpperCase() + "', phone = '" + client.getPhone() + "'", "client_id = '" + client.getClient_id() + "'");
        GetData.GetAll();
    }
}