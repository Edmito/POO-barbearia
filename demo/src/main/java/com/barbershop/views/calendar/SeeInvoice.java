package com.barbershop.views.calendar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.barbershop.controllers.database.GetData;
import com.barbershop.models.Client;
import com.barbershop.models.InvoiceProduct;
import com.barbershop.models.InvoiceService;
import com.barbershop.models.Product;
import com.barbershop.models.Service;

public class SeeInvoice implements Initializable {

    private List<InvoiceProduct> invoice_products = new ArrayList<>();
    private List<InvoiceService> invoice_services = new ArrayList<>();

    @FXML
    private Label dateLabel;
    @FXML
    private Label clientLabel;
    @FXML
    private Label taxLabel;
    @FXML
    private Label discountLabel;
    @FXML
    private Label subTotalLabel;
    @FXML
    private Label totalLabel;

    @FXML
    private ListView<InvoiceProduct> productsList;
    @FXML
    private ListView<InvoiceService> servicesList;

    //initialize the buttons
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taxLabel.setText(Calendar.selectedEvent.getInvoice().getTax() + "");
        discountLabel.setText(Calendar.selectedEvent.getInvoice().getReductions() + "");
        subTotalLabel.setText(Calendar.selectedEvent.getInvoice().getSub_total() + "");
        totalLabel.setText(Calendar.selectedEvent.getInvoice().getTotal() + "");
        for (Client c : GetData.AllClients) {
            if (c.getClient_id() == Calendar.selectedEvent.getClientId()) {
                clientLabel.setText(c.getFirst_name() + " " + c.getLast_name());
                break;
            }
        }
        List<Integer> products_ids = Calendar.selectedEvent.getInvoice().getProducts_ids();
        for (Integer i : products_ids) {
            for (Product p : GetData.AllProducts) {
                if (p.getProductId() == i) {
                    invoice_products.add(new InvoiceProduct(p));
                }
            }
        }
        List<InvoiceProduct> new_added_products = new ArrayList<>();
        for (InvoiceProduct ip : invoice_products) {
            boolean found = false;

            for (InvoiceProduct nip : new_added_products) {
                if (ip.getProduct().getProductId() == nip.getProduct().getProductId()) {
                    // Create a new instance of InvoiceProduct instead of modifying the existing one
                    InvoiceProduct newNip = new InvoiceProduct(nip.getProduct());
                    newNip.setQuantity(nip.getQuantity() + 1);
                    new_added_products.remove(nip);
                    new_added_products.add(newNip);
                    found = true;
                    break;
                }
            }
            if (!found) {
                new_added_products.add(new InvoiceProduct(ip.getProduct()));
            }
        }
        List<Integer> services_ids = Calendar.selectedEvent.getInvoice().getServices_ids();
        for (Integer i : services_ids) {
            for (Service s : GetData.AllServices) {
                if (s.getServiceId() == i) {
                    invoice_services.add(new InvoiceService(s));
                }
            }
        }
        dateLabel.setText(Calendar.selectedEvent.getDateAndTime());
        servicesList.getItems().addAll(invoice_services);
        productsList.getItems().addAll(new_added_products);
    }

    //display the wizard
    public static void openSeeInvoiceWizard() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(SeeInvoice.class.getResource("/com/barbershop/" + "SeeInvoice_Wizard.fxml"));
            Parent root = fxmlLoader.load();

            // Create the scene
            Scene scene = new Scene(root);

            // Create the stage
            Stage stage = new Stage();
            stage.getIcons().add(new Image(SeeInvoice.class.getResourceAsStream("invoice.png")));
            stage.setScene(scene);
            stage.setTitle("Invoice");
            stage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows until closed
            stage.setResizable(false); // Make the stage not resizable
            stage.showAndWait(); // Show the stage and wait for it to be closed

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
