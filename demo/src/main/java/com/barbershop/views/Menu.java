package com.barbershop.views;

import java.io.IOException;

import com.barbershop.App;
import com.barbershop.controllers.style.Colors;
import com.barbershop.controllers.style.HoverController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Menu {
    String page_selected = "Calendar";

    @FXML
    private Pane contentPane;

    private void loadContent(String fxmlFileName) {
        try {
            // Load the content from the specified FXML file
            Pane newContent = FXMLLoader.load(getClass().getResource("/com/barbershop/" + fxmlFileName));

            // Replace the content in the pane with the new content
            contentPane.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button agendaButton;

    @FXML
    private void handleAgendaButtonClick(ActionEvent event) {
        page_selected = "Calendar";
        switchPage();
    }

    @FXML
    private Button clientsButton;

    @FXML
    private void handleClientsButtonClick(ActionEvent event) {
        page_selected = "Clients";
        switchPage();
    }

    @FXML
    private Button stockButton;

    @FXML
    private void handleStockButtonClick(ActionEvent event) {
        page_selected = "Products";
        switchPage();
    }

    @FXML
    private Button servicesButton;

    @FXML
    private void handleServicesButtonClick(ActionEvent event) {
        page_selected = "Services";
        switchPage();
    }

    @FXML
    private Button exitButton;

    @FXML
    private void handleExitButtonClick(ActionEvent event) {
        page_selected = "Exit";
        switchPage();
    }

    @FXML
    private Button themeButton;

    @FXML
    private void handleThemeButtonClick(ActionEvent event) {
        Colors.setTheme(!Colors.isDark);
        Stage stage = (Stage) exitButton.getScene().getWindow();
        try {
            App.loadApp(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private Pane menuBar;

    @FXML
    public void initialize() {
        if (!Colors.isDark) {
            themeButton.setText("🌙");
            themeButton.setTooltip(new Tooltip("Switch to Dark Mode."));
            themeButton.getTooltip().setFont(new Font(13));
        } else {
            themeButton.setText("☀");
            themeButton.setTooltip(new Tooltip("Switch to Light Mode."));
            themeButton.getTooltip().setFont(new Font(13));
        }
        menuBar.setStyle("-fx-background-color: " + Colors.primary + ";");
        HoverController.addSideMenuHoverEffect(themeButton);
        switchPage();
    }

    private void switchPage() {
        switch (page_selected) {
            case "Calendar":
                loadContent("Calendar.fxml");
                HoverController.noSideMenuHoverEffect(agendaButton);
                HoverController.addSideMenuHoverEffect(stockButton);
                HoverController.addSideMenuHoverEffect(servicesButton);
                HoverController.addSideMenuHoverEffect(exitButton);
                HoverController.addSideMenuHoverEffect(clientsButton);
                break;
            case "Clients":
                loadContent("Clients.fxml");
                HoverController.noSideMenuHoverEffect(clientsButton);
                HoverController.addSideMenuHoverEffect(agendaButton);
                HoverController.addSideMenuHoverEffect(stockButton);
                HoverController.addSideMenuHoverEffect(servicesButton);
                HoverController.addSideMenuHoverEffect(exitButton);
                break;
            case "Products":
                loadContent("Products.fxml");
                HoverController.noSideMenuHoverEffect(stockButton);
                HoverController.addSideMenuHoverEffect(agendaButton);
                HoverController.addSideMenuHoverEffect(servicesButton);
                HoverController.addSideMenuHoverEffect(exitButton);
                HoverController.addSideMenuHoverEffect(clientsButton);
                break;
            case "Services":
                loadContent("Services.fxml");
                HoverController.noSideMenuHoverEffect(servicesButton);
                HoverController.addSideMenuHoverEffect(agendaButton);
                HoverController.addSideMenuHoverEffect(stockButton);
                HoverController.addSideMenuHoverEffect(exitButton);
                HoverController.addSideMenuHoverEffect(clientsButton);
                break;
            case "Exit":
                HoverController.addSideMenuHoverEffect(agendaButton);
                HoverController.addSideMenuHoverEffect(stockButton);
                HoverController.addSideMenuHoverEffect(servicesButton);
                HoverController.addSideMenuHoverEffect(exitButton);
                HoverController.addSideMenuHoverEffect(clientsButton);
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
                break;

            default:
                page_selected = "Calendar";
                break;
        }
    }

}
