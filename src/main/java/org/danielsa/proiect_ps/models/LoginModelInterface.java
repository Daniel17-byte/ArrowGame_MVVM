package org.danielsa.proiect_ps.models;

import org.danielsa.proiect_ps.DatabaseService;

public interface LoginModelInterface {
    boolean authenticate(String username, String password);
    DatabaseService getDatabaseService();
}
