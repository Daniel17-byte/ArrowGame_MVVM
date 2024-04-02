package org.danielsa.proiect_ps.models;

public interface RegisterAttemptHandler {
    void onRegisterAttempt(String username, String password, String userType);
}