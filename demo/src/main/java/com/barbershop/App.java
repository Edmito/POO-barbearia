package com.barbershop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        loadApp(stage);
    }

    public static void loadApp(@SuppressWarnings("exports") Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("My Barbershop App");
        stage.setScene(scene);

        //change the icon of the app
        stage.getIcons().add(new Image(App.class.getResourceAsStream("icon.png")));

        stage.show();

        // Ensure that the stage remains maximized after showing
        stage.setMaximized(true);
    }
}