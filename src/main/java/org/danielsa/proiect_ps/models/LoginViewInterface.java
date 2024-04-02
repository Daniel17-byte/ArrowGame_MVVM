package org.danielsa.proiect_ps.models;

public interface LoginViewInterface {
    void openRegisterWindow();
    void showLoginResult(boolean success);
    void setOnLoginAttempt(LoginAttemptHandler handler);
    void initComponents();
}
