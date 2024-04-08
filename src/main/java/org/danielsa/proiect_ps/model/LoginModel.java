package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.DatabaseService;
import org.danielsa.proiect_ps.Main;

public class LoginModel implements LoginModelInterface{
    private final DatabaseService databaseService;

    public LoginModel() {
        this.databaseService = Main.context.getBean(DatabaseService.class);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return databaseService.authenticate(username, password);
    }

}
