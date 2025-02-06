package com.barbershop.views.clients;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.barbershop.controllers.database.GetData;
import com.barbershop.controllers.style.Colors;
import com.barbershop.controllers.style.HoverController;
import com.barbershop.models.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Clients implements Initializable {
    public static Client selectedClient = null;

    @FXML
    private Button addButton;

    @FXML
    void onActionAddButton(ActionEvent event) {
        AddClient.openAddClientWizard();
        loadTable();
    }

    @FXML
    private Button updateButton;

    @FXML
    void onActionUpdateButton(ActionEvent event) {
        edit();
    }

    private void edit() {
        EditClient.openEditClientWizard();
        loadTable();
    }

    @FXML
    private Button deleteButton;

    @FXML
    void onActionDeleteButton(ActionEvent event) {
        DeleteClient.openDeleteClientWizard();
        loadTable();
    }

    @FXML
    private Button searchButton;

    @FXML
    void onActionSearchButton(ActionEvent event) {
        search();
    }

    private void search() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        List<Client> searchResults = new ArrayList<>();

        for (Client client : GetData.AllClients) {
            // Convert attributes to lowercase for case-insensitive comparison
            String clientName = client.getFirst_name().toLowerCase() + " " + client.getLast_name().toLowerCase();
            String phone = String.valueOf(client.getPhone());

            // Check if any attribute contains the search term
            if (clientName.contains(searchTerm) || phone.contains(searchTerm)) {
                // If the eve matches the search term, add it to the search results
                searchResults.add(client);
            }
        }

        // Clear the current items in the selectionList
        selectionList.getItems().clear();
        selectionList.getItems().addAll(searchResults);
    }

    @FXML
    private TableView<Client> table;
    @FXML
    private TableColumn<Client, Integer> id;
    @FXML
    private TableColumn<Client, String> firstName;
    @FXML
    private TableColumn<Client, String> lastName;
    @FXML
    private TableColumn<Client, Integer> phone;
    @FXML
    private TextField searchField;
    @FXML
    private ListView<Client> selectionList;

    public ObservableList<Client> list = FXCollections.observableArrayList();

    @FXML
    private HBox topMenu;
    @FXML
    private VBox rightMenu;
    @FXML
    private VBox leftMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        topMenu.setStyle("-fx-background-color: " + Colors.primary + "; -fx-padding: 10");
        rightMenu.setStyle("-fx-background-color: " + Colors.background);
        leftMenu.setStyle("-fx-background-color: " + Colors.background2);
        selectionList.setStyle("-fx-control-inner-background: " + Colors.background + ";" + "-fx-background-color: " + Colors.background);
        searchField.setStyle("-fx-background-color: " + Colors.secondary + "; -fx-text-fill: " + Colors.text2);
        table.setStyle("-fx-background-color: " + Colors.background2 + "; -fx-control-inner-background: " + Colors.background2 + "; -fx-table-cell-border-color: " + Colors.background + ";");

        loadTable();
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedClient = newSelection;
            selectionList.getItems().clear();
            selectionList.getItems().add(selectedClient);
        });
        // Add event listener for selectionList
        selectionList.setOnMouseClicked(event -> {
            selectedClient = selectionList.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2 && !event.isConsumed()) {
                event.consume();
                if (selectedClient != null) {
                    edit();
                }
            }
        });
        // Add event listener for selectionList
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !event.isConsumed()) {
                event.consume();
                if (selectedClient != null) {
                    edit();
                }
            }
        });
        //when Enter Key pressed in search field, run the search
        searchField.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        search();
                    }
                }
        );
        HoverController.addPopUpHoverEffect(addButton, "GREEN");
        HoverController.addPopUpHoverEffect(updateButton, "BLUE");
        HoverController.addPopUpHoverEffect(deleteButton, "RED");
        HoverController.setMenuButtonStyle(searchButton, "WHITESMOKE");
        HoverController.addMenuHoverEffect(searchButton, "#188c86");
    }

    private void loadTable() {
        selectedClient = null;
        list.clear();
        table.getItems().clear();
        selectionList.getItems().clear();
        GetData.GetAll();
        for (Client client : GetData.AllClients) {
            list.add(client);
        }
        id.setCellValueFactory(new PropertyValueFactory<Client, Integer>("client_id"));
        firstName.setCellValueFactory(new PropertyValueFactory<Client, String>("first_name"));
        lastName.setCellValueFactory(new PropertyValueFactory<Client, String>("last_name"));
        phone.setCellValueFactory(new PropertyValueFactory<Client, Integer>("phone"));
        table.setItems(list);
    }

}
