package org.danielsa.proiect_ps.viewmodel;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.danielsa.proiect_ps.model.LoginModel;
import org.danielsa.proiect_ps.model.LoginModelInterface;
import org.danielsa.proiect_ps.view.*;

public class LoginViewModel {
    private final LoginModelInterface model;

    public LoginViewModel() {
        model = new LoginModel();
    }

    public void openRegisterWindow() {
        RegisterView view = new RegisterView();
        Stage registerStage = new Stage();

        registerStage.setScene(view);
        registerStage.setTitle("Register");
        registerStage.show();
    }

    public void showLoginResult(Label resultLabel, boolean success) {
        if (success) {
            GameView view = new GameView();
            Stage gameStage = new Stage();

            gameStage.setScene(view);
            gameStage.setTitle("Arrow Game");
            gameStage.show();
        } else {
            resultLabel.setText("Login failed. Please try again.");
        }
    }

    public boolean authenticate(String username, String password) {
        return model.authenticate(username, password);
    }
}
