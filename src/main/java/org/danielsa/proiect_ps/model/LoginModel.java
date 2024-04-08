package org.danielsa.proiect_ps.model;

import jakarta.inject.Inject;
import org.danielsa.proiect_ps.DatabaseService;

public class LoginModel implements LoginModelInterface{
    @Inject
    private final DatabaseService databaseService;

    public LoginModel(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public boolean authenticate(String username, String password) {
        return databaseService.authenticate(username, password);
    }

    @Override
    public DatabaseService getDatabaseService() {
        return this.databaseService;
    }
}
