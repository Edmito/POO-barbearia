package com.barbershop.views.products;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.barbershop.controllers.database.GetData;
import com.barbershop.controllers.style.Colors;
import com.barbershop.controllers.style.HoverController;
import com.barbershop.models.Product;

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

public class Products implements Initializable {
    public static Product selectedProduct = null;

    @FXML
    private Button addButton;

    @FXML
    void onActionAddButton(ActionEvent event) {
        AddProduct.openAddProductWizard();
        loadTable();
    }

    @FXML
    private Button updateButton;

    @FXML
    void onActionUpdateButton(ActionEvent event) {
        edit();
    }

    private void edit() {
        EditProduct.openEditProductWizard();
        loadTable();
    }

    @FXML
    private Button deleteButton;

    @FXML
    void onActionDeleteButton(ActionEvent event) {
        DeleteProduct.openDeleteProductWizard();
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
        List<Product> searchResults = new ArrayList<>();

        for (Product product : GetData.AllProducts) {
            // Convert attributes to lowercase for case-insensitive comparison
            String NameDescription = product.getName().toLowerCase() + " " + product.getDescription().toLowerCase();
            String PriceQuantity = String.valueOf(product.getPrice()) + String.valueOf(product.getQuantity());

            // Check if any attribute contains the search term
            if (NameDescription.contains(searchTerm) || PriceQuantity.contains(searchTerm)) {
                // If the eve matches the search term, add it to the search results
                searchResults.add(product);
            }
        }

        // Clear the current items in the selectionList
        selectionList.getItems().clear();
        selectionList.getItems().addAll(searchResults);
    }

    @FXML
    private TableView<Product> table;
    @FXML
    private TableColumn<Product, Integer> id;
    @FXML
    private TableColumn<Product, String> name;
    @FXML
    private TableColumn<Product, String> description;
    @FXML
    private TableColumn<Product, Integer> quantity;
    @FXML
    private TableColumn<Product, Integer> price;
    @FXML
    private TextField searchField;
    @FXML
    private ListView<Product> selectionList;

    public ObservableList<Product> list = FXCollections.observableArrayList();

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
            selectedProduct = newSelection;
            selectionList.getItems().clear();
            selectionList.getItems().add(selectedProduct);
        });
        // Add event listener for selectionList
        selectionList.setOnMouseClicked(event -> {
            selectedProduct = selectionList.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2 && !event.isConsumed()) {
                event.consume();
                if (selectedProduct != null) {
                    edit();
                }
            }
        });
        // Add event listener for selectionList
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !event.isConsumed()) {
                event.consume();
                if (selectedProduct != null) {
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
        selectedProduct = null;
        list.clear();
        table.getItems().clear();
        selectionList.getItems().clear();
        GetData.GetAll();
        for (Product Product : GetData.AllProducts) {
            list.add(Product);
        }
        id.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productId"));
        name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        description.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        price.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        table.setItems(list);
    }
}
