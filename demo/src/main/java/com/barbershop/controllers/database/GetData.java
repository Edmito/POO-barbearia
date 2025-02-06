package com.barbershop.controllers.database;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.barbershop.models.*;

public class GetData {

    public static List<Event> AllEvents = new ArrayList<>();
    public static List<Product> AllProducts = new ArrayList<>();
    public static List<Service> AllServices = new ArrayList<>();
    public static List<Client> AllClients = new ArrayList<>();
    public static List<Invoice> AllInvoices = new ArrayList<>();

    public static void GetAll() {
        GetEvents();
        GetProducts();
        GetServices();
        GetInvoices();
        GetClients();
    }

    public static void GetEvents() {
        AllEvents.clear();
        List<List<String>> DBevents = DB.selectRow("db_barbershop", "Event", "event_id,date_time,client_id,invoice_id,service_id,description,type", "");
        if (DBevents != null && !DBevents.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (List<String> e : DBevents) {
                if (e != null && e.size() >= 7) {
                    Event event = new Event(
                        Integer.parseInt(e.get(0)), // event_id
                        LocalDateTime.parse(e.get(1), formatter), // date_time
                        Integer.parseInt(e.get(2)), // client_id
                        Integer.parseInt(e.get(3)), // invoice_id
                        Integer.parseInt(e.get(4)), // service_id
                        e.get(5), // description
                        Integer.parseInt(e.get(6)) // type
                    );
                    AllEvents.add(event);
                }
            }
        } else {
            System.out.println("Nenhum evento encontrado no banco de dados.");
        }
    }

    private static <T> List<T> getItemsByInvoice(int id, String tableName, String columnName, List<T> allItems, Function<T, Integer> idExtractor) {
        List<T> items = new ArrayList<>();
        List<List<String>> DBitems = DB.selectRow("db_barbershop", tableName, "invoice_id," + columnName, "WHERE invoice_id = '" + id + "'");
        for (List<String> dbItem : DBitems) {
            for (T item : allItems) {
                if (idExtractor.apply(item) == Integer.parseInt(dbItem.get(1))) {
                    items.add(item);
                }
            }
        }
        return items;
    }

    public static List<Product> getProductByInvoice(int id) {
        return getItemsByInvoice(id, "BillProduct", "product_id", AllProducts, Product::getProductId);
    }

    public static List<Integer> getProductIdByInvoice(int id) {
        return getItemsByInvoice(id, "BillProduct", "product_id", AllProducts, Product::getProductId)
                .stream()
                .map(Product::getProductId)
                .collect(Collectors.toList());
    }

    public static List<Service> getServiceByInvoice(int id) {
        return getItemsByInvoice(id, "BillService", "service_id", AllServices, Service::getServiceId);
    }

    public static List<Integer> getServiceIdByInvoice(int id) {
        return getItemsByInvoice(id, "BillService", "service_id", AllServices, Service::getServiceId)
                .stream()
                .map(Service::getServiceId)
                .collect(Collectors.toList());
    }

    public static void GetInvoices() {
        AllInvoices.clear();
        List<List<String>> DBinvoices = DB.selectRow("db_barbershop", "Invoice", "invoice_id,client_id,event_id,sub_total,reductions,tax,total", "");
        if (DBinvoices != null && !DBinvoices.isEmpty()) {
            for (List<String> i : DBinvoices) {
                List<Integer> products = getProductIdByInvoice(Integer.parseInt(i.get(0)));
                List<Integer> services = getServiceIdByInvoice(Integer.parseInt(i.get(0)));
                Invoice invoice = new Invoice(
                    Integer.parseInt(i.get(0)), // invoice_id
                    Integer.parseInt(i.get(1)), // client_id
                    Integer.parseInt(i.get(2)), // event_id
                    products, // products
                    services, // services
                    Double.parseDouble(i.get(3)), // sub_total
                    Double.parseDouble(i.get(4)), // reductions
                    Double.parseDouble(i.get(5)), // tax
                    Double.parseDouble(i.get(6)) // total
                );
                AllInvoices.add(invoice);
            }
        } else {
            System.out.println("Nenhuma fatura encontrada no banco de dados.");
        }
    }

    public static void GetProducts() {
        AllProducts.clear();
        List<List<String>> DBproducts = DB.selectRow("db_barbershop", "Product", "product_id,name,description,quantity,price", "");
        if (DBproducts != null && !DBproducts.isEmpty()) {
            for (List<String> p : DBproducts) {
                Product product = new Product(
                    Integer.parseInt(p.get(0)), // product_id
                    p.get(1), // name
                    p.get(2), // description
                    Integer.parseInt(p.get(3)), // quantity
                    Double.parseDouble(p.get(4)) // price
                );
                AllProducts.add(product);
            }
        } else {
            System.out.println("Nenhum produto encontrado no banco de dados.");
        }
    }

    public static void GetServices() {
        AllServices.clear();
        List<List<String>> DBservices = DB.selectRow("db_barbershop", "Service", "service_id,name,description,price", "");
        if (DBservices != null && !DBservices.isEmpty()) {
            for (List<String> p : DBservices) {
                Service service = new Service(
                    Integer.parseInt(p.get(0)), // service_id
                    p.get(1), // name
                    p.get(2), // description
                    Double.parseDouble(p.get(3)) // price
                );
                AllServices.add(service);
            }
        } else {
            System.out.println("Nenhum serviço encontrado no banco de dados.");
        }
    }

    public static List<Event> getClientEvents(int id) {
        return AllEvents.stream()
                .filter(event -> event.getClientId() == id)
                .collect(Collectors.toList());
    }

    public static List<Invoice> getClientInvoices(int id) {
        return AllInvoices.stream()
                .filter(invoice -> invoice.getClientId() == id)
                .collect(Collectors.toList());
    }

    public static void GetClients() {
        AllClients.clear();
        List<List<String>> DBclients = DB.selectRow("db_barbershop", "Client", "client_id,first_name,last_name,phone", "");
        if (DBclients != null && !DBclients.isEmpty()) {
            for (List<String> c : DBclients) {
                List<Event> events = getClientEvents(Integer.parseInt(c.get(0)));
                List<Invoice> invoices = getClientInvoices(Integer.parseInt(c.get(0)));
                Client client = new Client(
                    Integer.parseInt(c.get(0)), // client_id
                    c.get(1), // first_name
                    c.get(2), // last_name
                    Integer.parseInt(c.get(3)), // phone
                    events, // events
                    invoices // invoices
                );
                AllClients.add(client);
            }
        } else {
            System.out.println("Nenhum cliente encontrado no banco de dados.");
        }
    }

    public static Client findClientByPhone(int phone) {
        for (Client client : AllClients) {
            if (client.getPhone() == phone) {
                return client;
            }
        }
        return null; // Return null if no client is found with the given phone number
    }
}