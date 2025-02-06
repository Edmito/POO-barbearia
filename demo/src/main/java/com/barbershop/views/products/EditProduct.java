package com.barbershop.views.products;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import com.barbershop.controllers.alerts.AlertController;
import com.barbershop.controllers.database.UpdateData;
import com.barbershop.controllers.style.Colors;
import com.barbershop.controllers.style.HoverController;
import com.barbershop.models.Product;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class EditProduct implements Initializable {
    private Product newProduct = null;

    @FXML
    private Spinner<Integer> quantityField;
    @FXML
    private Spinner<Double> priceField;

    @FXML
    private TextField productNameField;
    @FXML
    private TextField descriptionField;

    @FXML
    private Button saveButton;

    @FXML
    void onActionSaveButton(ActionEvent event) {
        update();
    }

    //add product button
    public void update() {
        if (productNameField.getText().isEmpty()) {
            AlertController.showWarning("Invalid Input", "Please enter a valid input!");
        } else {
            newProduct.setName(productNameField.getText());
            newProduct.setDescription(descriptionField.getText());
            newProduct.setPrice(priceField.getValue());
            newProduct.setQuantity(quantityField.getValue());
            UpdateData.UpdateProduct(newProduct);
            closeWindow();
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

    @FXML
    private VBox background;
    @FXML
    private Label titleLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label priceLabel;

    //initialize the buttons
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setStyle("-fx-text-fill: " + Colors.text + "; -fx-font-size: 16px; -fx-font-weight: bold;");
        background.setStyle("-fx-background-color: " + Colors.background);
        nameLabel.setStyle("-fx-text-fill: " + Colors.text);
        descriptionLabel.setStyle("-fx-text-fill: " + Colors.text);
        quantityLabel.setStyle("-fx-text-fill: " + Colors.text);
        priceLabel.setStyle("-fx-text-fill: " + Colors.text);

        productNameField.setStyle("-fx-background-color: " + Colors.background2 + ";" + "-fx-text-fill: " + Colors.text);
        descriptionField.setStyle("-fx-background-color: " + Colors.background2 + ";" + "-fx-text-fill: " + Colors.text);

        priceField.setStyle("-fx-background-color: " + Colors.background2 + "; -fx-text-fill: " + Colors.text + ";");
        priceField.getEditor().setStyle("-fx-background-color: " + Colors.background2 + "; -fx-text-fill: " + Colors.text + ";");

        quantityField.setStyle("-fx-background-color: " + Colors.background2 + "; -fx-text-fill: " + Colors.text + ";");
        quantityField.getEditor().setStyle("-fx-background-color: " + Colors.background2 + "; -fx-text-fill: " + Colors.text + ";");

        newProduct = Products.selectedProduct;
        productNameField.setText(Products.selectedProduct.getName());
        descriptionField.setText(Products.selectedProduct.getDescription());
        // Initialize Spinner for hour selection
        SpinnerValueFactory<Integer> hourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 100, Products.selectedProduct.getQuantity());
        hourValueFactory.setWrapAround(true); // Enable wrapping around
        quantityField.setValueFactory(hourValueFactory);
        quantityField.getValueFactory().setConverter(new StringConverter<>() {
            @Override
            public String toString(Integer value) {
                return String.format("%02d", value); // Format value to "00"
            }

            @Override
            public Integer fromString(String value) {
                return Integer.valueOf(value); // Convert back to Integer
            }
        });

        // Initialize Spinner for minites selection
        SpinnerValueFactory<Double> priceValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 999.99, Products.selectedProduct.getPrice());
        priceValueFactory.setWrapAround(true); // Enable wrapping around
        priceField.setValueFactory(priceValueFactory);

        priceField.getValueFactory().setConverter(new StringConverter<Double>() {
            @Override
            public String toString(Double value) {
                // Format value to "00.00" with dot as the decimal separator
                return String.format(Locale.ENGLISH, "%.2f", value);
            }

            @Override
            public Double fromString(String value) {
                return Double.valueOf(value); // Convert back to Double
            }
        });
        HoverController.addPopUpHoverEffect(saveButton, "GREEN");
        HoverController.addPopUpHoverEffect(cancelButton, "RED");
    }

    //display the wizard
    public static void openEditProductWizard() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(EditProduct.class.getResource("/com/barbershop/" + "UpdateProduct_Wizard.fxml"));
            Parent root = fxmlLoader.load();

            // Create the scene
            Scene scene = new Scene(root);

            // Create the stage
            Stage stage = new Stage();
            stage.getIcons().add(new Image(EditProduct.class.getResourceAsStream("product.png")));
            stage.setScene(scene);
            stage.setTitle("Edit Product");
            stage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows until closed
            stage.setResizable(false); // Make the stage not resizable
            stage.showAndWait(); // Show the stage and wait for it to be closed

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
