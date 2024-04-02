package org.danielsa.proiect_ps.views;

import jakarta.inject.Inject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.models.RegisterAttemptHandler;
import org.danielsa.proiect_ps.models.RegisterViewInterface;
import org.danielsa.proiect_ps.presenters.DatabaseService;

import java.io.IOException;

@Getter
public class RegisterView extends Scene implements RegisterViewInterface {
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField userTypeField;
    private Button registerButton;
    private Label resultLabel;

    @Inject
    private final DatabaseService databaseService;

    public RegisterView(DatabaseService databaseService) {
        super(new VBox(), 300, 200);
        this.databaseService = databaseService;
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

        resultLabel = new Label();

        root.getChildren().addAll(usernameField, passwordField, userTypeField, registerButton);
    }

    @Override
    public void setOnRegisterAttempt(RegisterAttemptHandler handler) {
        registerButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String userType = userTypeField.getText();
            handler.onRegisterAttempt(username, password, userType);
        });
    }

    @Override
    public void showRegisterResult(boolean success) {
        if (success) {
            Stage primaryStage = (Stage) this.getWindow();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);

                primaryStage.setScene(scene);
                primaryStage.setTitle("Arrow Game");

                primaryStage.show();

                GameView gameView = fxmlLoader.getController();
                gameView.setDatabaseService(databaseService);
                gameView.loadWonGames();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            resultLabel.setText("Register failed. Please try again.");
        }
    }
}
