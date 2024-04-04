package org.danielsa.proiect_ps.views;

import jakarta.inject.Inject;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import org.danielsa.proiect_ps.models.GameViewInterface;
import org.danielsa.proiect_ps.models.RegisterViewInterface;
import org.danielsa.proiect_ps.presenters.DatabaseService;
import org.danielsa.proiect_ps.presenters.RegisterPresenter;

@Getter
public class RegisterView extends Scene implements RegisterViewInterface {
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField userTypeField;
    private Button registerButton;
    private Label resultLabel;

    private final RegisterPresenter presenter;

    @Inject
    private final DatabaseService databaseService;

    public RegisterView(DatabaseService databaseService) {
        super(new VBox(), 300, 200);
        this.databaseService = databaseService;
        this.presenter = new RegisterPresenter(this);
        initComponents();
    }

    @Override
    public void initComponents() {
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
            boolean authenticated = presenter.getModel().register(username, password, userType);
            presenter.getView().showRegisterResult(authenticated);
        });

        resultLabel = new Label();

        root.getChildren().addAll(usernameField, passwordField, userTypeField, registerButton);
    }

    @SuppressWarnings("CastCanBeRemovedNarrowingVariableType")
    @Override
    public void showRegisterResult(boolean success) {
        if (success) {
            GameViewInterface view = new GameView(getDatabaseService());
            Stage gameStage = new Stage();

            gameStage.setScene((GameView) view);
            gameStage.setTitle("Arrow Game");
            gameStage.show();
        } else {
            resultLabel.setText("Register failed. Please try again.");
        }
    }
}
