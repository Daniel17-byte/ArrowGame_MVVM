package org.danielsa.proiect_ps.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.danielsa.proiect_ps.viewmodel.RegisterViewModel;

public class RegisterView extends Scene implements RegisterViewInterface {
    private final RegisterViewModel viewModel;

    public RegisterView() {
        super(new VBox(), 300, 200);
        this.viewModel = new RegisterViewModel();
        initComponents();
    }

    @Override
    public void initComponents() {
        TextField usernameField;
        PasswordField passwordField;
        TextField userTypeField;
        Button registerButton;
        Label resultLabel = new Label();

        VBox root = (VBox) getRoot();
        root.setSpacing(10);
        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        userTypeField = new TextField();
        userTypeField.setPromptText("User Type");

        registerButton = new Button("Register");

        registerButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String userType = userTypeField.getText();
            boolean authenticated = viewModel.register(username, password, userType);
            viewModel.showRegisterResult(resultLabel, authenticated);
        });

        root.getChildren().addAll(usernameField, passwordField, userTypeField, registerButton);
    }
}
