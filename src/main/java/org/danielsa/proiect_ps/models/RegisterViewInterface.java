package org.danielsa.proiect_ps.models;

public interface RegisterViewInterface {
    void initComponents();
    void setOnRegisterAttempt(RegisterAttemptHandler handler);
    void showRegisterResult(boolean success);
}
