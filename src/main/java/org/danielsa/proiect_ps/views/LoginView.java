package org.danielsa.proiect_ps.views;

import jakarta.inject.Inject;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.Getter;
import org.danielsa.proiect_ps.models.*;
import org.danielsa.proiect_ps.presenters.DatabaseService;
import org.danielsa.proiect_ps.presenters.LoginPresenter;

@Getter
public class LoginView extends Scene implements LoginViewInterface {
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Label resultLabel;
    private Button registerButton;

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
            boolean authenticated = presenter.getModel().authenticate(username, password);
            presenter.getView().showLoginResult(authenticated);
        });

        registerButton.setOnAction(event -> openRegisterWindow());

        resultLabel = new Label();

        root.getChildren().addAll(usernameField, passwordField, loginButton, resultLabel, registerButton);
    }

    @SuppressWarnings("CastCanBeRemovedNarrowingVariableType")
    @Override
    public void showLoginResult(boolean success) {
        if (success) {
            GameViewInterface view = new GameView(getDatabaseService());
            Stage gameStage = new Stage();

            gameStage.setScene((GameView) view);
            gameStage.setTitle("Arrow Game");
            gameStage.show();
        } else {
            resultLabel.setText("Login failed. Please try again.");
        }
    }

    @SuppressWarnings("CastCanBeRemovedNarrowingVariableType")
    @Override
    public void openRegisterWindow() {
        RegisterViewInterface view = new RegisterView(databaseService);
        Stage registerStage = new Stage();

        registerStage.setScene((RegisterView) view);
        registerStage.setTitle("Register");
        registerStage.show();
    }
}
