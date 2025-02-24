package com.barbershop.controllers.database;

public class InitializeDatabase {
    String dbName = "db_barbershop";

    public InitializeDatabase() {
        // Criar o banco de dados
        DB.createDB(dbName);

        // Criar tabelas
        String clientTable = "client_id INT NOT NULL AUTO_INCREMENT, first_name VARCHAR(255) DEFAULT NULL, " +
                             "last_name VARCHAR(255) DEFAULT NULL, phone INT DEFAULT NULL, PRIMARY KEY (client_id)";
        DB.createTable(dbName, "client", clientTable);

        String productTable = "product_id INT NOT NULL AUTO_INCREMENT, name VARCHAR(255) DEFAULT NULL, " +
                              "description TEXT DEFAULT NULL, quantity INT DEFAULT NULL, price DOUBLE DEFAULT NULL, " +
                              "PRIMARY KEY (product_id)";
        DB.createTable(dbName, "product", productTable);

        String serviceTable = "service_id INT NOT NULL AUTO_INCREMENT, name VARCHAR(255) DEFAULT NULL, " +
                              "description TEXT DEFAULT NULL, price DOUBLE DEFAULT NULL, PRIMARY KEY (service_id)";
        DB.createTable(dbName, "service", serviceTable);

        String eventTable = "event_id INT NOT NULL AUTO_INCREMENT, date_time DATETIME DEFAULT NULL, " +
                            "client_id INT DEFAULT NULL, service_id INT DEFAULT NULL, description TEXT DEFAULT NULL, " +
                            "type INT DEFAULT NULL, invoice_id INT DEFAULT NULL, PRIMARY KEY (event_id), " +
                            "KEY (client_id), KEY (service_id), FOREIGN KEY (client_id) REFERENCES client (client_id), " +
                            "FOREIGN KEY (service_id) REFERENCES service (service_id)";
        DB.createTable(dbName, "event", eventTable);

        String invoiceTable = "invoice_id INT NOT NULL AUTO_INCREMENT, client_id INT DEFAULT NULL, event_id INT DEFAULT NULL, " +
                              "sub_total DOUBLE DEFAULT NULL, reductions DOUBLE DEFAULT NULL, tax DOUBLE DEFAULT NULL, " +
                              "total DOUBLE DEFAULT NULL, PRIMARY KEY (invoice_id), KEY (client_id), KEY (event_id), " +
                              "FOREIGN KEY (client_id) REFERENCES client (client_id), FOREIGN KEY (event_id) REFERENCES event (event_id)";
        DB.createTable(dbName, "invoice", invoiceTable);

        String billServiceTable = "invoice_id INT NOT NULL, service_id INT NOT NULL, PRIMARY KEY (invoice_id, service_id), " +
                                  "KEY (service_id), FOREIGN KEY (invoice_id) REFERENCES invoice (invoice_id), " +
                                  "FOREIGN KEY (service_id) REFERENCES service (service_id)";
        DB.createTable(dbName, "billservice", billServiceTable);

        String billProductTable = "invoice_id INT DEFAULT NULL, product_id INT DEFAULT NULL, KEY (invoice_id), " +
                                  "KEY (product_id), FOREIGN KEY (invoice_id) REFERENCES invoice (invoice_id), " +
                                  "FOREIGN KEY (product_id) REFERENCES product (product_id)";
        DB.createTable(dbName, "billproduct", billProductTable);

        System.out.println("Banco de dados e tabelas criados com sucesso!");
    }
}