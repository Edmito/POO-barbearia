package com.barbershop.views.clients;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.barbershop.controllers.alerts.AlertController;
import com.barbershop.controllers.database.AddData;
import com.barbershop.controllers.patterns.PaternController;
import com.barbershop.controllers.style.Colors;
import com.barbershop.controllers.style.HoverController;
import com.barbershop.models.Client;

public class AddClient implements Initializable {

    @FXML
    private TextField clientFirstNameField;
    @FXML
    private TextField clientLastNameField;
    @FXML
    private TextField clientPhoneField;

    @FXML
    private Button saveButton;

    @FXML
    void onActionSaveButton(ActionEvent event) {
        addProduct();
    }

    //add product button
    public void addProduct() {
        if (!PaternController.isValidName(clientFirstNameField.getText()) || !PaternController.isValidName(clientLastNameField.getText()) || !PaternController.isValidPhoneNumber(clientPhoneField.getText())) {
            AlertController.showWarning("Entrada Inválida", "Por favor, insira uma entrada válida!");
        } else {
            Client newClient = new Client(clientFirstNameField.getText(), clientLastNameField.getText(), Integer.parseInt(clientPhoneField.getText()), new ArrayList<>(), new ArrayList<>());
            newClient.setClient_id(AddData.AddClient(newClient));
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
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label phoneLabel;

    //initialize the buttons
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setStyle("-fx-text-fill: " + Colors.text + "; -fx-font-size: 16px; -fx-font-weight: bold;");
        background.setStyle("-fx-background-color: " + Colors.background);
        firstNameLabel.setStyle("-fx-text-fill: " + Colors.text);
        lastNameLabel.setStyle("-fx-text-fill: " + Colors.text);
        phoneLabel.setStyle("-fx-text-fill: " + Colors.text);

        clientFirstNameField.setStyle("-fx-background-color: " + Colors.background2 + ";" + "-fx-text-fill: " + Colors.text);
        clientLastNameField.setStyle("-fx-background-color: " + Colors.background2 + ";" + "-fx-text-fill: " + Colors.text);
        clientPhoneField.setStyle("-fx-background-color: " + Colors.background2 + ";" + "-fx-text-fill: " + Colors.text);


        clientPhoneField.setTextFormatter(new TextFormatter<>(PaternController.createPatternFilter("#########")));

        HoverController.addPopUpHoverEffect(saveButton, "GREEN");
        HoverController.addPopUpHoverEffect(cancelButton, "RED");
    }

    //display the wizard
    public static void openAddClientWizard() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(AddClient.class.getResource("/com/barbershop/" + "AddClient_Wizard.fxml"));
            Parent root = fxmlLoader.load();

            // Create the scene
            Scene scene = new Scene(root);

            // Create the stage
            Stage stage = new Stage();
            stage.getIcons().add(new Image(AddClient.class.getResourceAsStream("client.png")));
            stage.setScene(scene);
            stage.setTitle("Adicionar Cliente");
            stage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows until closed
            stage.setResizable(false); // Make the stage not resizable
            stage.showAndWait(); // Show the stage and wait for it to be closed

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
