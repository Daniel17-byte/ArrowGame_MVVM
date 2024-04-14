package org.danielsa.proiect_ps.view;

import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.danielsa.proiect_ps.viewmodel.RegisterViewModel;

public class RegisterView extends Scene {
    private final RegisterViewModel viewModel;

    public RegisterView() {
        super(new VBox(), 300, 200);
        this.viewModel = new RegisterViewModel();
        initComponents();
    }

    public void initComponents() {
        TextField usernameField;
        PasswordField passwordField;
        Button registerButton;
        Label resultLabel = new Label();
        ComboBox<String> userTypeComboBox = new ComboBox<>();

        VBox root = (VBox) getRoot();
        root.setSpacing(10);
        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        userTypeComboBox.getItems().addAll("ADMIN", "PLAYER");
        userTypeComboBox.setPromptText("Select User Type");

        registerButton = new Button("Register");

        registerButton.setOnAction(event -> viewModel.showRegisterResult());

        Bindings.bindBidirectional(resultLabel.textProperty(), viewModel.getResultLabelProperty());
        Bindings.bindBidirectional(usernameField.textProperty(), viewModel.getUsernameProperty());
        Bindings.bindBidirectional(passwordField.textProperty(), viewModel.getPasswordProperty());
        Bindings.bindBidirectional(userTypeComboBox.valueProperty(), viewModel.getUserTypeProperty());

        root.getChildren().addAll(usernameField, passwordField, userTypeComboBox, registerButton);
    }
}
