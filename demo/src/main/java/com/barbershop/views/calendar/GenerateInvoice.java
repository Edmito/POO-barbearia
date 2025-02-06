package com.barbershop.views.calendar;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.barbershop.controllers.alerts.AlertController;
import com.barbershop.controllers.alerts.StockAlert;
import com.barbershop.controllers.database.AddData;
import com.barbershop.controllers.database.GetData;
import com.barbershop.controllers.database.UpdateData;
import com.barbershop.controllers.style.HoverController;
import com.barbershop.models.Invoice;
import com.barbershop.models.InvoiceProduct;
import com.barbershop.models.InvoiceService;
import com.barbershop.models.Product;
import com.barbershop.models.Service;

public class GenerateInvoice implements Initializable {
    private Invoice newInvoice = null;

    private Product selected_product = null;
    private Service selected_service = null;

    private List<InvoiceProduct> added_products = new ArrayList<>();
    private List<InvoiceService> added_services = new ArrayList<>();

    private List<Product> products = new ArrayList<>();
    private List<Service> services = new ArrayList<>();
    private int tax = 20;
    private int discount = 0;
    private double subTotal = 0.0;
    private double total = 0.0;

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

    @FXML
    private ComboBox<String> productField;
    @FXML
    private ComboBox<String> serviceField;

    @FXML
    private Spinner<Integer> taxField;
    @FXML
    private Spinner<Integer> discountField;

    @FXML
    private Button addProduct;

    @FXML
    void onActionAddProductButton() {
        if (selected_product != null && selected_product.getQuantity() > 0) {
            boolean quantityValid = true;

            int selectedProductCount = 0;
            for (Product ip : products) {
                if (ip.getProductId() == selected_product.getProductId()) {
                    selectedProductCount++;
                    if (selectedProductCount == selected_product.getQuantity()) {
                        quantityValid = false;
                        break;
                    }
                }
            }

            // If quantity is valid, add the selected product to both lists and reload the products list
            if (quantityValid) {
                products.add(selected_product);
                added_products.add(new InvoiceProduct(selected_product));
                reloadProductsList();
            }
        }
        if (selected_product.getQuantity() == 0) {
            AlertController.showWarning("Stock Alert", "No stock left in product " + selected_product.getName() + "!");
        }

    }

    private void reloadProductsList() {
        List<InvoiceProduct> new_added_products = new ArrayList<>();
        for (InvoiceProduct ip : added_products) {
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

        productsList.getItems().clear();
        productsList.getItems().addAll(new_added_products);
        calculateTotal();
    }

    private void calculateTotal() {
        subTotal = 0.0;
        for (InvoiceProduct ip : productsList.getItems()) {
            subTotal += ip.getPrice();
        }
        for (Service s : services) {
            subTotal += s.getPrice();
        }
        total = subTotal + (subTotal * tax / 100) - (subTotal * discount / 100);
        subTotalLabel.setText(subTotal + "");
        totalLabel.setText(total + "");
    }

    @FXML
    private Button addService;

    @FXML
    void onActionAddServiceButton() {
        if (selected_service != null) {
            services.add(selected_service);
            added_services.add(new InvoiceService(selected_service));
            reloadServicesList();
        }
    }

    private void reloadServicesList() {
        servicesList.getItems().clear();
        servicesList.getItems().addAll(added_services);
        calculateTotal();
    }

    @FXML
    private Button updateTax;

    @FXML
    void onActionUpdateTaxButton() {
        tax = taxField.getValue();
        taxLabel.setText(taxField.getValue() + "");
        calculateTotal();
    }

    @FXML
    private Button updateDiscount;

    @FXML
    void onActionUpdateDiscountButton() {
        discount = discountField.getValue();
        discountLabel.setText(discountField.getValue() + "");
        calculateTotal();
    }

    @FXML
    private Button saveButton;

    @FXML
    void onActionSaveButton(ActionEvent event) {
        addInvoice();
    }

    //add event button
    public void addInvoice() {
        try {
            List<Integer> Lproducts = new ArrayList<>();
            for (Product p : products) {
                Lproducts.add(p.getProductId());
            }
            List<Integer> Lservices = new ArrayList<>();
            for (Service s : services) {
                Lservices.add(s.getServiceId());
            }
            //cerate the invoice
            newInvoice = new Invoice(Calendar.selectedEvent.getClientId(), Calendar.selectedEvent.getEventId(), Lproducts, Lservices, subTotal, discount, tax, total);
            newInvoice.setInvoice_id(AddData.AddInvoice(newInvoice));
            //update the event
            Calendar.selectedEvent.setInvoiceId(newInvoice.getInvoice_id());
            Calendar.selectedEvent.setType(2);
            UpdateData.UpdateEvent(Calendar.selectedEvent);
            //close the wizard
            closeWindow();
        } catch (StockAlert noProduct) {
            AlertController.showError("Stock Alert!", noProduct.getMessage());
        }
    }

    @FXML
    private Button cancelButton;

    @FXML
    void onActionCancelButton(ActionEvent event) {
        closeWindow();
    }

    //handle on close window
    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    //initialize the buttons
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientLabel.setText(Calendar.selectedEvent.getClient().getFirst_name() + " " + Calendar.selectedEvent.getClient().getLast_name());
        dateLabel.setText(Calendar.selectedEvent.getDateAndTime());

        // Initialize ComboBox with sample data
        for (Service s : GetData.AllServices) {
            serviceField.getItems().add(s.getName());
        }
        if (!GetData.AllServices.isEmpty()) {
            serviceField.setValue(GetData.AllServices.get(0).getName());
            selected_service = GetData.AllServices.get(0);
        } else {
            serviceField.setValue("none");
        }

        //Listner on conbo box service
        serviceField.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, String t, String t1) {
                for (Service s : GetData.AllServices) {
                    if (s.getName().equals(t1)) {
                        selected_service = s;
                        break;
                    }
                }
            }
        });

        for (Product p : GetData.AllProducts) {
            productField.getItems().add(p.getName());
        }
        if (!GetData.AllProducts.isEmpty()) {
            productField.setValue(GetData.AllProducts.get(0).getName());
            selected_product = GetData.AllProducts.get(0);
        } else {
            productField.setValue("none");
        }

        //Listner on conbo box service
        productField.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, String t, String t1) {
                for (Product p : GetData.AllProducts) {
                    if (p.getName().equals(t1)) {
                        selected_product = p;
                        break;
                    }
                }
            }
        });

        // Initialize Spinner for tax selection
        SpinnerValueFactory<Integer> taxValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 30, 20);
        taxValueFactory.setWrapAround(true); // Enable wrapping around
        taxField.setValueFactory(taxValueFactory);
        taxField.getValueFactory().setConverter(new StringConverter<>() {
            @Override
            public String toString(Integer value) {
                return String.format("%02d", value); // Format value to "00"
            }

            @Override
            public Integer fromString(String value) {
                return Integer.valueOf(value); // Convert back to Integer
            }
        });

        // Initialize Spinner for discount selection
        SpinnerValueFactory<Integer> discountValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 95, 0);
        discountValueFactory.setWrapAround(true); // Enable wrapping around
        discountField.setValueFactory(discountValueFactory);
        discountField.getValueFactory().setConverter(new StringConverter<>() {
            @Override
            public String toString(Integer value) {
                return String.format("%02d", value); // Format value to "00"
            }

            @Override
            public Integer fromString(String value) {
                return Integer.valueOf(value); // Convert back to Integer
            }
        });

        // Add event listener for selectionList
        productsList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !event.isConsumed()) {
                event.consume();
                if (productsList.getSelectionModel().getSelectedItem() != null) {
                    for (Product p : products) {
                        if (p.getProductId() == productsList.getSelectionModel().getSelectedItem().getProduct().getProductId()) {
                            products.remove(p);
                            break;
                        }
                    }
                    for (InvoiceProduct ip : added_products) {
                        if (ip.getProduct().getProductId() == productsList.getSelectionModel().getSelectedItem().getProduct().getProductId()) {
                            added_products.remove(ip);
                            break;
                        }
                    }
                    reloadProductsList();
                }
            }
        });

        // Add event listener for selectionList
        servicesList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !event.isConsumed()) {
                event.consume();
                if (servicesList.getSelectionModel().getSelectedItem() != null) {
                    for (Service s : services) {
                        if (s.getServiceId() == servicesList.getSelectionModel().getSelectedItem().getService().getServiceId()) {
                            services.remove(s);
                            break;
                        }
                    }
                    for (InvoiceService ip : servicesList.getItems()) {
                        if (ip.getService().getServiceId() == servicesList.getSelectionModel().getSelectedItem().getService().getServiceId()) {
                            added_services.remove(ip);
                            break;
                        }
                    }
                    reloadServicesList();
                }
            }
        });

        HoverController.addPopUpHoverEffect(saveButton, "GREEN");
        HoverController.addPopUpHoverEffect(cancelButton, "RED");
    }

    //display the wizard
    public static void openGenerateInvoiceWizard() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(GenerateInvoice.class.getResource("/com/barbershop/" + "GenerateInvoice_Wizard.fxml"));
            Parent root = fxmlLoader.load();

            // Create the scene
            Scene scene = new Scene(root);

            // Create the stage
            Stage stage = new Stage();
            stage.getIcons().add(new Image(GenerateInvoice.class.getResourceAsStream("invoice.png")));
            stage.setScene(scene);
            stage.setTitle("Generate an invoice");
            stage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows until closed
            stage.setResizable(false); // Make the stage not resizable
            stage.showAndWait(); // Show the stage and wait for it to be closed


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
