package org.danielsa.proiect_ps.viewmodel;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.Getter;
import org.danielsa.proiect_ps.model.LoginModel;
import org.danielsa.proiect_ps.model.LoginModelInterface;
import org.danielsa.proiect_ps.view.*;

@Getter
public class LoginViewModel {
    private final LoginViewInterface view;
    private final LoginModelInterface model;

    public LoginViewModel(LoginView view) {
        this.view = view;
        model = new LoginModel();
    }

    @SuppressWarnings("CastCanBeRemovedNarrowingVariableType")
    public void openRegisterWindow() {
        RegisterViewInterface view = new RegisterView();
        Stage registerStage = new Stage();

        registerStage.setScene((RegisterView) view);
        registerStage.setTitle("Register");
        registerStage.show();
    }

    @SuppressWarnings("CastCanBeRemovedNarrowingVariableType")
    public void showLoginResult(Label resultLabel, boolean success) {
        if (success) {
            GameViewInterface view = new GameView();
            Stage gameStage = new Stage();

            gameStage.setScene((GameView) view);
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
