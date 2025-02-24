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
import javafx.scene.control.RadioButton;
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
import com.barbershop.controllers.database.UpdateData;
import com.barbershop.controllers.patterns.PaternController;
import com.barbershop.controllers.style.Colors;
import com.barbershop.controllers.style.HoverController;
import com.barbershop.models.Client;
import com.barbershop.models.Event;
import com.barbershop.models.Service;

public class EditEvent implements Initializable {
    private Service selected_service = null;
    private Client selected_client = null;
    private int event_state = -1;
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
    private RadioButton radioCanceled;
    @FXML
    private RadioButton radioDone;
    @FXML
    private RadioButton radioNotDone;

    @FXML
    private Button saveButton;

    @FXML
    void onActionSaveButton(ActionEvent event) {
        addEvent();
    }

    public void addEvent() {
        if (!PaternController.isValidName(clientFirstNameField.getText()) || 
            !PaternController.isValidName(clientLastNameField.getText()) || 
            !PaternController.isValidPhoneNumber(clientPhoneField.getText())) {
            AlertController.showWarning("Entrada Inválida", "Por favor, insira uma entrada válida!");
            return;
        }
    
        // Verifica se o cliente já existe no banco
        for (Client c : GetData.AllClients) {
            if (c.getFirst_name().equalsIgnoreCase(clientFirstNameField.getText()) && 
                c.getLast_name().equalsIgnoreCase(clientLastNameField.getText()) && 
                c.getPhone() == Integer.parseInt(clientPhoneField.getText())) {
                selected_client = c;
                break;
            }
        }
    
        // Se o cliente não foi encontrado, cria um novo
        if (selected_client == null) {
            selected_client = new Client(clientFirstNameField.getText(), clientLastNameField.getText(), 
                                         Integer.parseInt(clientPhoneField.getText()), new ArrayList<>(), new ArrayList<>());
            int clientId = AddData.AddClient(selected_client);
            selected_client.setClient_id(clientId);
        }
    
        // Obtém a data e hora do evento
        LocalDate selectedDate = dateField.getValue();
        int selectedHour = hourField.getValue();
        int selectedMinute = minuteField.getValue();
        LocalDateTime eventDateTime = LocalDateTime.of(selectedDate, LocalTime.of(selectedHour, selectedMinute));
    
        // Cria o evento com o ID correto do cliente
        newEvent = new Event(Calendar.selectedEvent.getEventId(), eventDateTime, 
                             selected_client.getClient_id(), 0, 
                             selected_service.getServiceId(), descriptionField.getText(), event_state);
    
        // Verifica conflitos de horário antes de salvar
        for (Event e : GetData.AllEvents) {
            if (e.getDateTime().equals(newEvent.getDateTime()) && 
                e.getEventId() != Calendar.selectedEvent.getEventId()) {
                AlertController.showWarning("Conflito", "O novo evento conflita com um evento existente.");
                return;
            }
        }
    
        // Atualiza o evento no banco de dados
        UpdateData.UpdateEvent(newEvent);
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
    @FXML
    private Label stateLabel;

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
        stateLabel.setStyle("-fx-text-fill: " + Colors.text + ";");

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

        event_state = Calendar.selectedEvent.getType();
        clientFirstNameField.setText(Calendar.selectedEvent.getClient().getFirst_name());
        clientLastNameField.setText(Calendar.selectedEvent.getClient().getLast_name());
        clientPhoneField.setText(Calendar.selectedEvent.getClient().getPhone() + "");
        selected_client = Calendar.selectedEvent.getClient();
        selected_service = Calendar.selectedEvent.getService();
        descriptionField.setText(Calendar.selectedEvent.getDecription());
        dateField.setValue(Calendar.selectedEvent.getDateTime().toLocalDate());
        SpinnerValueFactory<Integer> hourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 23, Calendar.selectedEvent.getDateTime().getHour());
        hourValueFactory.setWrapAround(true); // Enable wrapping around
        hourField.setValueFactory(hourValueFactory);
        SpinnerValueFactory<Integer> miniteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59, Calendar.selectedEvent.getDateTime().getMinute());
        miniteValueFactory.setWrapAround(true); // Enable wrapping around
        minuteField.setValueFactory(miniteValueFactory);

        switch (Calendar.selectedEvent.getType()) {
            case 0:
                radioCanceled.setSelected(true);
                break;
            case 1:
                radioNotDone.setSelected(true);
                break;
            case 2:
                radioDone.setSelected(true);
                break;
            default:
                radioNotDone.setSelected(true);
                break;
        }

        radioCanceled.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    event_state = 0;
                }
            }
        });

        radioNotDone.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    event_state = 1;
                }
            }
        });

        radioDone.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    event_state = 2;
                }
            }
        });

        // Initialize ComboBox with sample data
        for (Service s : GetData.AllServices) {
            service.getItems().add(s.getName());
        }
        if (!GetData.AllServices.isEmpty()) {
            service.setValue(GetData.AllServices.get(0).getName());
        } else {
            service.setValue("nenhum serviço");
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
                    clientPhoneField.setText("" + c.getPhone());
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
                    clientPhoneField.setText("" + c.getPhone());
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

        dateField.valueProperty().addListener((ov, oldValue, newValue) -> {
            dateField.setValue(newValue);
        });

        // Apply text formatter to clientPhoneField
        clientPhoneField.setTextFormatter(new TextFormatter<>(PaternController.createPatternFilter("#########")));

        HoverController.addPopUpHoverEffect(saveButton, "GREEN");
        HoverController.addPopUpHoverEffect(cancelButton, "RED");
    }

    //display the wizard
    public static void openEditEventWizard() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(EditEvent.class.getResource("/com/barbershop/" + "UpdateEvent_Wizard.fxml"));
            Parent root = fxmlLoader.load();

            // Create the scene
            Scene scene = new Scene(root);

            // Create the stage
            Stage stage = new Stage();
            stage.getIcons().add(new Image(EditEvent.class.getResourceAsStream("add.png")));
            stage.setScene(scene);
            stage.setTitle("Atualizar Evento");
            stage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows until closed
            stage.setResizable(false); // Make the stage not resizable
            stage.showAndWait(); // Show the stage and wait for it to be closed

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}