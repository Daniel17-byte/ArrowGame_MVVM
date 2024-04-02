package org.danielsa.proiect_ps.models;

public interface LoginAttemptHandler {
    void onLoginAttempt(String username, String password);
}