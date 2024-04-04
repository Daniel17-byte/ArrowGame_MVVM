package org.danielsa.proiect_ps.models;

import jakarta.inject.Inject;
import org.danielsa.proiect_ps.DatabaseService;

public class AdminModel implements AdminModelInterface{
    @Inject
    private final DatabaseService authService;

    public AdminModel(DatabaseService authService) {
        this.authService = authService;
    }
}
