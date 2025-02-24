package com.barbershop.views.clients;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.barbershop.controllers.alerts.AlertController;
import com.barbershop.controllers.database.UpdateData;
import com.barbershop.controllers.patterns.PaternController;
import com.barbershop.controllers.style.Colors;
import com.barbershop.controllers.style.HoverController;
import com.barbershop.models.Client;

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

public class EditClient implements Initializable {
    private Client newClient = null;

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
        update();
    }

    //add product button
    public void update() {
        if (!PaternController.isValidName(clientFirstNameField.getText()) || !PaternController.isValidName(clientLastNameField.getText()) || !PaternController.isValidPhoneNumber(clientPhoneField.getText())) {
            AlertController.showWarning("Entrada Inválida", "Por favor, insira uma entrada válida!");
        } else {
            newClient.setFirst_name(clientFirstNameField.getText());
            newClient.setLast_name(clientLastNameField.getText());
            newClient.setPhone(Integer.parseInt(clientPhoneField.getText()));
            UpdateData.UpdateClient(newClient);
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

        newClient = Clients.selectedClient;
        clientFirstNameField.setText(Clients.selectedClient.getFirst_name());
        clientLastNameField.setText(Clients.selectedClient.getLast_name());
        clientPhoneField.setText(String.valueOf(Clients.selectedClient.getPhone()));
        clientPhoneField.setTextFormatter(new TextFormatter<>(PaternController.createPatternFilter("#########")));

        HoverController.addPopUpHoverEffect(saveButton, "GREEN");
        HoverController.addPopUpHoverEffect(cancelButton, "RED");
    }

    //display the wizard
    public static void openEditClientWizard() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(EditClient.class.getResource("/com/barbershop/" + "UpdateClient_Wizard.fxml"));
            Parent root = fxmlLoader.load();

            // Create the scene
            Scene scene = new Scene(root);

            // Create the stage
            Stage stage = new Stage();
            stage.getIcons().add(new Image(EditClient.class.getResourceAsStream("client.png")));
            stage.setScene(scene);
            stage.setTitle("Editar Cliente");
            stage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows until closed
            stage.setResizable(false); // Make the stage not resizable
            stage.showAndWait(); // Show the stage and wait for it to be closed

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
