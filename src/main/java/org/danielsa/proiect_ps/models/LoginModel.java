package org.danielsa.proiect_ps.models;

import jakarta.inject.Inject;
import org.danielsa.proiect_ps.presenters.DatabaseService;

public class LoginModel {
    @Inject
    private final DatabaseService authService;

    public LoginModel(DatabaseService authService) {
        this.authService = authService;
    }

    public boolean authenticate(String username, String password) {
        return authService.authenticate(username, password);
    }
}
