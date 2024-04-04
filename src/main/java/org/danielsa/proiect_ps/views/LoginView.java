package org.danielsa.proiect_ps.views;

import jakarta.inject.Inject;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import lombok.Getter;
import org.danielsa.proiect_ps.DatabaseService;
import org.danielsa.proiect_ps.presenters.LoginPresenter;

@Getter
public class LoginView extends Scene implements LoginViewInterface {
    private final LoginPresenter presenter;
    @Inject
    private final DatabaseService databaseService;

    public LoginView(DatabaseService databaseService) {
        super(new VBox(), 300, 200);
        this.databaseService = databaseService;
        this.presenter = new LoginPresenter(this);
        initComponents();
    }

    @Override
    public void initComponents() {
        TextField usernameField;
        PasswordField passwordField;
        Button loginButton;
        Label resultLabel = new Label();
        Button registerButton;

        VBox root = (VBox) getRoot();
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        loginButton = new Button("Login");

        registerButton = new Button("Register");

        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            boolean authenticated = presenter.authenticate(username, password);
            presenter.showLoginResult(resultLabel, authenticated);
        });

        registerButton.setOnAction(event -> presenter.openRegisterWindow());

        root.getChildren().addAll(usernameField, passwordField, loginButton, resultLabel, registerButton);
    }

}
