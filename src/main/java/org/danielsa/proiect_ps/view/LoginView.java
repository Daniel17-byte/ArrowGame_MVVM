package org.danielsa.proiect_ps.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.danielsa.proiect_ps.viewmodel.LoginViewModel;

public class LoginView extends Scene implements LoginViewInterface {
    private final LoginViewModel viewModel;

    public LoginView() {
        super(new VBox(), 300, 200);
        this.viewModel = new LoginViewModel();
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
            boolean authenticated = viewModel.authenticate(username, password);
            viewModel.showLoginResult(resultLabel, authenticated);
        });

        registerButton.setOnAction(event -> viewModel.openRegisterWindow());

        root.getChildren().addAll(usernameField, passwordField, loginButton, resultLabel, registerButton);
    }

}
