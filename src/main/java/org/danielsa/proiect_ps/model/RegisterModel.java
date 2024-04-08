package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.DatabaseService;
import org.danielsa.proiect_ps.Main;

public class RegisterModel implements RegisterModelInterface{
    private final DatabaseService databaseService;

    public RegisterModel() {
        this.databaseService = Main.context.getBean(DatabaseService.class);
    }

    @Override
    public boolean register(String username, String password, String usertype) {
        return databaseService.register(username, password, usertype);
    }

}
