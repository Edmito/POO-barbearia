package com.barbershop.controllers.style;

public class Colors {
    public static boolean isDark = false;
    public static String primary = "#56097a";
    public static String secondary = "#34054a";
    public static String forground = "#c052f2";
    public static String background = "#242424";
    public static String background2 = "#1a1a1a";
    public static String background3 = "BLACK";
    public static String text = "WHITE";
    public static String text2 = "WHITESMOKE";

    public static void setTheme(boolean is_Dark) {
        isDark = is_Dark;
        if (is_Dark) {
            primary = "#56097a";
            secondary = "#34054a";
            forground = "#c052f2";

            background = "#242424";
            background2 = "#1a1a1a";
            background3 = "BLACK";

            text = "WHITE";
            text2 = "WHITE";
        } else {
            primary = "LIGHTSEAGREEN";
            secondary = "#148c86";

            forground = "WHITESMOKE";

            background = "WHITESMOKE";
            background2 = "#f0f0f0";
            background3 = "GREY";

            text = "BLACK";
            text2 = "WHITE";
        }
    }
}
