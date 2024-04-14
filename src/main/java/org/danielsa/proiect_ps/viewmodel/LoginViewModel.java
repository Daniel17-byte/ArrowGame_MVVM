package org.danielsa.proiect_ps.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;
import lombok.Getter;
import org.danielsa.proiect_ps.model.LoginModel;
import org.danielsa.proiect_ps.model.LoginModelInterface;
import org.danielsa.proiect_ps.view.*;

public class LoginViewModel {
    private final LoginModelInterface model;
    @Getter
    private final StringProperty resultLabelProperty = new SimpleStringProperty();
    @Getter
    private final StringProperty usernameProperty = new SimpleStringProperty();
    @Getter
    private final StringProperty passwordProperty = new SimpleStringProperty();

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

    public void showLoginResult() {
        boolean success = model.authenticate(usernameProperty.getValue(), passwordProperty.getValue());
        if (success) {
            GameView view = new GameView();
            Stage gameStage = new Stage();

            gameStage.setScene(view);
            gameStage.setTitle("Arrow Game");
            gameStage.show();
        } else {
            resultLabelProperty.setValue("Login failed. Please try again.");
        }
    }

}
