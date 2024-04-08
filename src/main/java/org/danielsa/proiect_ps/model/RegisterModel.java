package org.danielsa.proiect_ps.model;

import jakarta.inject.Inject;
import org.danielsa.proiect_ps.DatabaseService;

public class RegisterModel implements RegisterModelInterface{
    @Inject
    private final DatabaseService databaseService;

    public RegisterModel(DatabaseService authService) {
        this.databaseService = authService;
    }

    @Override
    public boolean register(String username, String password, String usertype) {
        return databaseService.register(username, password, usertype);
    }

    @Override
    public DatabaseService getDatabaseService() {
        return databaseService;
    }
}
