package com.barbershop;

import com.barbershop.controllers.database.GetData;
import com.barbershop.controllers.style.Colors;

public class Main {
    public static void main(String[] args) {
        GetData.GetAll();
        Colors.setTheme(false);
        App.launch(App.class, args);
    }
}
