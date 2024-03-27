package org.danielsa.proiect_ps.models;

import jakarta.inject.Inject;
import org.danielsa.proiect_ps.presenters.DatabaseService;

public class RegisterModel {
    @Inject
    private final DatabaseService authService;

    public RegisterModel(DatabaseService authService) {
        this.authService = authService;
    }

    public boolean register(String username, String password, String usertype) {
        return authService.register(username, password, usertype);
    }
}
