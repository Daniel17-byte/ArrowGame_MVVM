package org.danielsa.proiect_ps.models;

import jakarta.inject.Inject;
import org.danielsa.proiect_ps.DatabaseService;

public class LoginModel implements LoginModelInterface{
    @Inject
    private final DatabaseService authService;

    public LoginModel(DatabaseService authService) {
        this.authService = authService;
    }

    @Override
    public boolean authenticate(String username, String password) {
        return authService.authenticate(username, password);
    }
}
