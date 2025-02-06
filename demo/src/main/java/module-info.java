module com.barbershop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;

    opens com.barbershop to javafx.fxml;
    exports com.barbershop;

    opens com.barbershop.views to javafx.fxml;
    exports com.barbershop.views;
    opens com.barbershop.views.calendar to javafx.fxml;
    exports com.barbershop.views.calendar;
    opens com.barbershop.views.products to javafx.fxml;
    exports com.barbershop.views.products;
    opens com.barbershop.views.services to javafx.fxml;
    exports com.barbershop.views.services;
    opens com.barbershop.views.clients to javafx.fxml;
    exports com.barbershop.views.clients;

    opens com.barbershop.models to javafx.fxml;
    exports com.barbershop.models;

    opens com.barbershop.controllers.database to javafx.fxml;
    exports com.barbershop.controllers.database;
    opens com.barbershop.controllers.patterns to javafx.fxml;
    exports com.barbershop.controllers.patterns;
    opens com.barbershop.controllers.alerts to javafx.fxml;
    exports com.barbershop.controllers.alerts;
    opens com.barbershop.controllers.style to javafx.fxml;
    exports com.barbershop.controllers.style;

}
