package com.barbershop;

import com.barbershop.controllers.database.GetData;
import com.barbershop.controllers.database.InitializeDatabase;
import com.barbershop.controllers.style.Colors;

public class Main {
    public static void main(String[] args) {
        new InitializeDatabase();
        // Carregar dados e configurar tema
        GetData.GetAll();
        Colors.setTheme(false);

        // Iniciar a aplicação
        App.launch(App.class, args);
}
}