package com.barbershop.controllers.style;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class HoverController {

    public static void setVBoxStyleWithBorder(@SuppressWarnings("exports") VBox VBox, String background_color, String border_color, int border_width, int radius) {
        VBox.setStyle("-fx-border-color: " + border_color + "; -fx-border-width: " + border_width + "; -fx-border-radius: " + radius + "; -fx-background-radius: 10; -fx-background-color:" + background_color + "; -fx-padding: 3;");
    }

    public static void addBorderHoverEffect(@SuppressWarnings("exports") VBox VBox, String hover_background_color, String hover_border_color, int hover_border_width, int hover_radius, String normal_background_color, String normal_border_color, int normal_border_width, int normal_radius) {
        VBox.setOnMouseEntered(event -> setVBoxStyleWithBorder(VBox, hover_background_color, hover_border_color, hover_border_width, hover_radius));
        VBox.setOnMouseExited(event -> setVBoxStyleWithBorder(VBox, normal_background_color, normal_border_color, normal_border_width, normal_radius));
    }

    public static void setPopUpButtonStyle(@SuppressWarnings("exports") Button button, String color, String borderColor, String textColor) {
        button.setStyle("-fx-border-color: " + borderColor + "; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-color:  " + color + "; -fx-text-fill: " + textColor + " ; -fx-font-weight: bold; -fx-font-size: 13;");
    }

    //set the hover of the buttons
    public static void addPopUpHoverEffect(@SuppressWarnings("exports") Button button, String color) {
        button.setOnMouseEntered(event -> setPopUpButtonStyle(button, "#ace3f2", "blue", "blue"));
        button.setOnMouseExited(event -> setPopUpButtonStyle(button, color, "transparent", "WHITE"));
    }

    /**
     * Sets the style for a button of the menu.
     *
     * @param button The button to set style for.
     * @param color  The color of the button.
     */
    public static void setMenuButtonStyle(@SuppressWarnings("exports") Button button, String color) {
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: " + color + "; -fx-font-weight: bold; -fx-font-size: 20; -fx-padding: 0;");
    }

    /**
     * Adds hover effect to a button of the menu.
     *
     * @param button     The button to add hover effect to.
     * @param hoverColor The color when hovering.
     */
    public static void addMenuHoverEffect(@SuppressWarnings("exports") Button button, String hoverColor) {
        button.setOnMouseEntered(event -> setMenuButtonStyle(button, hoverColor));
        button.setOnMouseExited(event -> setMenuButtonStyle(button, Colors.forground));
    }

    public static void noMenuHoverEffect(@SuppressWarnings("exports") Button button, String hoverColor) {
        button.setOnMouseEntered(event -> setMenuButtonStyle(button, hoverColor));
        button.setOnMouseExited(event -> setMenuButtonStyle(button, hoverColor));
    }

    /**
     * Sets the style for a button of the sub menu.
     *
     * @param button The button to set style for.
     * @param color  The color of the button.
     */
    public static void setSubMenuButtonStyle(@SuppressWarnings("exports") Button button, String color) {
        button.setStyle("-fx-background-color: transparent ; -fx-text-fill: " + color + "; -fx-font-weight: bold; -fx-font-size: 30;");
    }

    /**
     * Adds hover effect to a button of the sub menu.
     *
     * @param button     The button to add hover effect to.
     * @param hoverColor The color when hovering.
     */
    public static void addSubMenuHoverEffect(@SuppressWarnings("exports") Button button, String hoverColor) {
        button.setOnMouseEntered(event -> setSubMenuButtonStyle(button, hoverColor));
        button.setOnMouseExited(event -> setSubMenuButtonStyle(button, Colors.forground));
    }

    public static void addSideMenuHoverEffect(@SuppressWarnings("exports") Button button) {
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: " + Colors.forground + "; -fx-font-weight: bold; -fx-font-size: 30; ");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: " + Colors.secondary + "; -fx-font-weight: bold; -fx-font-size: 30; "));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: " + Colors.forground + "; -fx-font-weight: bold; -fx-font-size: 30; "));
    }

    public static void noSideMenuHoverEffect(@SuppressWarnings("exports") Button button) {
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: " + Colors.secondary + "; -fx-font-weight: bold; -fx-font-size: 30; ");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: " + Colors.secondary + "; -fx-font-weight: bold; -fx-font-size: 30; "));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: " + Colors.secondary + "; -fx-font-weight: bold; -fx-font-size: 30; "));
    }
}
