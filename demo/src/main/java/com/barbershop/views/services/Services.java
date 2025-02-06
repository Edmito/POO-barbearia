package com.barbershop.views.services;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.barbershop.controllers.database.GetData;
import com.barbershop.controllers.style.Colors;
import com.barbershop.controllers.style.HoverController;
import com.barbershop.models.Service;

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

public class Services implements Initializable {
    public static Service selectedService = null;

    @FXML
    private Button addButton;

    @FXML
    void onActionAddButton(ActionEvent event) {
        AddService.openAddServiceWizard();
        loadTable();
    }

    @FXML
    private Button updateButton;

    @FXML
    void onActionUpdateButton(ActionEvent event) {
        edit();
    }

    private void edit() {
        EditService.openEditServiceWizard();
        loadTable();
    }

    @FXML
    private Button deleteButton;

    @FXML
    void onActionDeleteButton(ActionEvent event) {
        DeleteService.openDeleteServiceWizard();
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
        List<Service> searchResults = new ArrayList<>();

        for (Service service : GetData.AllServices) {
            // Convert attributes to lowercase for case-insensitive comparison
            String NameDescription = service.getName().toLowerCase() + " " + service.getDescription().toLowerCase();
            String PriceQuantity = String.valueOf(service.getPrice());

            // Check if any attribute contains the search term
            if (NameDescription.contains(searchTerm) || PriceQuantity.contains(searchTerm)) {
                // If the eve matches the search term, add it to the search results
                searchResults.add(service);
            }
        }

        // Clear the current items in the selectionList
        selectionList.getItems().clear();
        selectionList.getItems().addAll(searchResults);
    }

    @FXML
    private TableView<Service> table;
    @FXML
    private TableColumn<Service, Integer> id;
    @FXML
    private TableColumn<Service, String> name;
    @FXML
    private TableColumn<Service, String> description;
    @FXML
    private TableColumn<Service, Integer> price;
    @FXML
    private TextField searchField;
    @FXML
    private ListView<Service> selectionList;

    public ObservableList<Service> list = FXCollections.observableArrayList();

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
        searchField.setStyle("-fx-background-color: " + Colors.secondary + "; -fx-text-fill:" + Colors.text2);
        table.setStyle("-fx-background-color: " + Colors.background2 + "; -fx-control-inner-background: " + Colors.background2 + "; -fx-table-cell-border-color: " + Colors.background + ";");

        loadTable();
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedService = newSelection;
            selectionList.getItems().clear();
            selectionList.getItems().add(selectedService);
        });
        // Add event listener for selectionList
        selectionList.setOnMouseClicked(event -> {
            selectedService = selectionList.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2 && !event.isConsumed()) {
                event.consume();
                if (selectedService != null) {
                    edit();
                }
            }
        });
        // Add event listener for selectionList
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !event.isConsumed()) {
                event.consume();
                if (selectedService != null) {
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
        selectedService = null;
        list.clear();
        table.getItems().clear();
        selectionList.getItems().clear();
        GetData.GetAll();
        for (Service service : GetData.AllServices) {
            list.add(service);
        }
        id.setCellValueFactory(new PropertyValueFactory<Service, Integer>("serviceId"));
        name.setCellValueFactory(new PropertyValueFactory<Service, String>("name"));
        description.setCellValueFactory(new PropertyValueFactory<Service, String>("description"));
        price.setCellValueFactory(new PropertyValueFactory<Service, Integer>("price"));
        table.setItems(list);
    }
}
