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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.barbershop.controllers.alerts.AlertController;
import com.barbershop.controllers.database.AddData;
import com.barbershop.controllers.database.GetData;
import com.barbershop.controllers.patterns.PaternController;
import com.barbershop.controllers.style.Colors;
import com.barbershop.controllers.style.HoverController;
import com.barbershop.models.Client;
import com.barbershop.models.Event;
import com.barbershop.models.Service;

public class AddEvent implements Initializable {
    private Service selected_service = null;
    private Client selected_client = null;
    private Event newEvent = null;

    @FXML
    private DatePicker dateField;
    @FXML
    private Spinner<Integer> hourField;
    @FXML
    private Spinner<Integer> minuteField;

    @FXML
    private TextField clientFirstNameField;
    @FXML
    private TextField clientLastNameField;
    @FXML
    private TextField clientPhoneField;
    @FXML
    private ComboBox<String> service;
    @FXML
    private TextField descriptionField;

    @FXML
    private Button saveButton;

    @FXML
    void onActionSaveButton(ActionEvent event) {
        addEvent();
    }

    //add event button
    public void addEvent() {
        if (!PaternController.isValidName(clientFirstNameField.getText()) || !PaternController.isValidName(clientLastNameField.getText()) || !PaternController.isValidPhoneNumber(clientPhoneField.getText())) {
            AlertController.showWarning("Invalid Input", "Please enter a valid input!");
        } else if (selected_client != null) {
            boolean found = false;
            for (Client c : GetData.AllClients) {
                if (c.getClient_id() == selected_client.getClient_id()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                selected_client = new Client(clientFirstNameField.getText(), clientLastNameField.getText(), Integer.parseInt(clientPhoneField.getText()), new ArrayList<>(), new ArrayList<>());
                selected_client.setClient_id(AddData.AddClient(selected_client));
            }
        } else if (selected_client == null) {
            selected_client = new Client(clientFirstNameField.getText(), clientLastNameField.getText(), Integer.parseInt(clientPhoneField.getText()), new ArrayList<>(), new ArrayList<>());
            selected_client.setClient_id(AddData.AddClient(selected_client));
        }

        // Combine the selected date, hour, and minute into a LocalDateTime object
        LocalDateTime eventDateTime = LocalDateTime.of(dateField.getValue(), LocalTime.of(hourField.getValue(), minuteField.getValue()));

        newEvent = new Event(eventDateTime, selected_client.getClient_id(), 0, selected_service.getServiceId(), descriptionField.getText());

        // Check if the new event conflicts with existing events
        if (newEvent != null) {
            if (newEvent.getDateTime().isBefore(LocalDateTime.now())) {
                AlertController.showWarning("Invalid Date", "The event date must be after the current date and time!");
            } else {
                boolean hasConflict = false;
                for (Event e : GetData.AllEvents) {
                    if (e.getDateTime().equals(newEvent.getDateTime())) {
                        hasConflict = true;
                        break;
                    }
                }
                // If there is a conflict, show an alert
                if (hasConflict) {
                    AlertController.showWarning("Conflict", "The new event conflicts with an existing event.");
                    return;
                } else {
                    newEvent.setClientId(selected_client.getClient_id());
                    AddData.AddEvent(newEvent);
                    // Close the window
                    closeWindow();
                }
            }
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
    private Label dateLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label serviceLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label phoneLabel;

    //initialize the buttons
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setStyle("-fx-text-fill: " + Colors.text + "; -fx-font-size: 16px; -fx-font-weight: bold;");
        dateLabel.setStyle("-fx-text-fill: " + Colors.text + ";");
        timeLabel.setStyle("-fx-text-fill: " + Colors.text + ";");
        serviceLabel.setStyle("-fx-text-fill: " + Colors.text + ";");
        nameLabel.setStyle("-fx-text-fill: " + Colors.text + ";");
        phoneLabel.setStyle("-fx-text-fill: " + Colors.text + ";");
        descriptionLabel.setStyle("-fx-text-fill: " + Colors.text + ";");

        background.setStyle("-fx-background-color: " + Colors.background);
        clientLastNameField.setStyle("-fx-background-color: " + Colors.background2 + "; -fx-text-fill: " + Colors.text + ";");
        clientFirstNameField.setStyle("-fx-background-color: " + Colors.background2 + "; -fx-text-fill: " + Colors.text + ";");
        clientPhoneField.setStyle("-fx-background-color: " + Colors.background2 + "; -fx-text-fill: " + Colors.text + ";");
        descriptionField.setStyle("-fx-background-color: " + Colors.background2 + "; -fx-text-fill: " + Colors.text + ";");

        dateField.setStyle("-fx-background-color: " + Colors.background2 + "; -fx-text-fill: " + Colors.text + ";");
        dateField.getEditor().setStyle("-fx-background-color: " + Colors.background2 + "; -fx-text-fill: " + Colors.text + ";");

        hourField.setStyle("-fx-background-color: " + Colors.background2 + "; -fx-text-fill: " + Colors.text + ";");
        hourField.getEditor().setStyle("-fx-background-color: " + Colors.background2 + "; -fx-text-fill: " + Colors.text + ";");

        minuteField.setStyle("-fx-background-color: " + Colors.background2 + "; -fx-text-fill: " + Colors.text + ";");
        minuteField.getEditor().setStyle("-fx-background-color: " + Colors.background2 + "; -fx-text-fill: " + Colors.text + ";");

        service.setStyle("-fx-background-color: " + Colors.background2 + "; -fx-text-fill: " + Colors.text + "; -fx-selection-bar-text: " + Colors.text + "; -fx-control-inner-background: " + Colors.background2 + ";");
        service.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item);
                    setStyle("-fx-background-color: " + Colors.background2 + "; -fx-text-fill: " + Colors.text);
                } else {
                    setText(null);
                }
            }
        });


        // Initialize ComboBox with sample data
        for (Service s : GetData.AllServices) {
            service.getItems().add(s.getName());
        }
        if (!GetData.AllServices.isEmpty()) {
            service.setValue(GetData.AllServices.get(0).getName());
            selected_service = GetData.AllServices.get(0);
            if (!selected_service.getDescription().isEmpty()) {
                //innitialize the description
                descriptionField.setText(selected_service.getDescription());
            }
        } else {
            service.setValue("none");
        }

        //Listner on conbo box service
        service.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, String t, String t1) {
                for (Service s : GetData.AllServices) {
                    if (s.getName().equals(t1)) {
                        selected_service = s;
                        if (!selected_service.getDescription().isEmpty()) {
                            //innitialize the description
                            descriptionField.setText(selected_service.getDescription());
                        }
                        break;
                    }
                }
            }
        });

        clientFirstNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean found = false;
            for (Client c : GetData.AllClients) {
                if (c.getFirst_name().toLowerCase().equals(newValue.toLowerCase())) {
                    clientLastNameField.setText(c.getLast_name().toUpperCase());
                    clientPhoneField.setText(String.valueOf(c.getPhone()));
                    selected_client = c;
                    found = true;
                    break;
                }
            }
            if (!found) {
                selected_client = null;
            }
        });

        clientLastNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean found = false;
            for (Client c : GetData.AllClients) {
                if (c.getLast_name().toLowerCase().equals(newValue.toLowerCase())) {
                    clientFirstNameField.setText(c.getFirst_name());
                    clientPhoneField.setText(String.valueOf(c.getPhone()));
                    selected_client = c;
                    found = true;
                    break;
                }
            }
            if (!found) {
                selected_client = null;
            }
        });

        clientPhoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean found = false;
            for (Client c : GetData.AllClients) {
                if (c.getPhone() == Integer.parseInt(newValue)) {
                    clientFirstNameField.setText(c.getFirst_name());
                    clientLastNameField.setText(c.getLast_name().toUpperCase());
                    selected_client = c;
                    found = true;
                    break;
                }
            }
            if (!found) {
                selected_client = null;
            }
        });

        // Initialize Spinner for hour selection
        SpinnerValueFactory<Integer> hourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 23, LocalDateTime.now().getHour());
        hourValueFactory.setWrapAround(true); // Enable wrapping around
        hourField.setValueFactory(hourValueFactory);
        hourField.getValueFactory().setConverter(new StringConverter<>() {
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
        SpinnerValueFactory<Integer> miniteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59, LocalDateTime.now().getMinute());
        miniteValueFactory.setWrapAround(true); // Enable wrapping around
        minuteField.setValueFactory(miniteValueFactory);

        minuteField.getValueFactory().setConverter(new StringConverter<>() {
            @Override
            public String toString(Integer value) {
                return String.format("%02d", value); // Format value to "00"
            }

            @Override
            public Integer fromString(String value) {
                return Integer.valueOf(value); // Convert back to Integer
            }
        });

        // Initialize the date
        if (Calendar.selected_date != null) {
            dateField.setValue(Calendar.selected_date);
        } else {
            dateField.setValue(LocalDate.now());
        }

        dateField.valueProperty().addListener((ov, oldValue, newValue) -> {
            dateField.setValue(newValue);
        });

        // Apply text formatter to clientPhoneField
        clientPhoneField.setTextFormatter(new TextFormatter<>(PaternController.createPatternFilter("#########")));

        HoverController.addPopUpHoverEffect(saveButton, "GREEN");
        HoverController.addPopUpHoverEffect(cancelButton, "RED");
    }

    //display the wizard
    public static void openAddEventWizard() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(AddEvent.class.getResource("/com/barbershop/" + "AddEvent_Wizard.fxml"));
            Parent root = fxmlLoader.load();

            // Create the scene
            Scene scene = new Scene(root);

            // Create the stage
            Stage stage = new Stage();
            stage.getIcons().add(new Image(AddEvent.class.getResourceAsStream("add.png")));
            stage.setScene(scene);
            stage.setTitle("Add Event");
            stage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows until closed
            stage.setResizable(false); // Make the stage not resizable
            stage.showAndWait(); // Show the stage and wait for it to be closed

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
