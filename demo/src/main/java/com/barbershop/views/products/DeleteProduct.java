package com.barbershop.views.products;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.barbershop.controllers.database.DeleteData;
import com.barbershop.controllers.style.Colors;
import com.barbershop.controllers.style.HoverController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DeleteProduct implements Initializable {

    @FXML
    private Button deleteButton;

    @FXML
    void onActionDeleteButton(ActionEvent event) {
        delete();
    }

    //add product button
    public void delete() {
        DeleteData.DeleteProduct(Products.selectedProduct.getProductId());
        closeWindow();
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

    //initialize the buttons
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setStyle("-fx-text-fill: " + Colors.text + "; -fx-font-size: 16px; -fx-font-weight: bold;");
        background.setStyle("-fx-background-color: " + Colors.background);

        HoverController.addPopUpHoverEffect(deleteButton, "GREEN");
        HoverController.addPopUpHoverEffect(cancelButton, "RED");
    }

    //display the wizard
    public static void openDeleteProductWizard() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(DeleteProduct.class.getResource("/com/barbershop/" + "DeleteProduct_Wizard.fxml"));
            Parent root = fxmlLoader.load();

            // Create the scene
            Scene scene = new Scene(root);

            // Create the stage
            Stage stage = new Stage();
            stage.getIcons().add(new Image(DeleteProduct.class.getResourceAsStream("product.png")));
            stage.setScene(scene);
            stage.setTitle("Delete Product");
            stage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows until closed
            stage.setResizable(false); // Make the stage not resizable
            stage.showAndWait(); // Show the stage and wait for it to be closed

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
