package org.danielsa.proiect_ps;

import jakarta.inject.Inject;
import javafx.application.Application;
import javafx.stage.Stage;
import org.danielsa.proiect_ps.models.LoginModel;
import org.danielsa.proiect_ps.presenters.DatabaseService;
import org.danielsa.proiect_ps.presenters.LoginPresenter;
import org.danielsa.proiect_ps.views.LoginView;

public class Login extends Application {

    @Inject
    private final DatabaseService databaseService = new DatabaseService();

    @Override
    public void start(Stage primaryStage) {
        LoginView loginView = new LoginView(databaseService);
        LoginModel loginModel = new LoginModel(databaseService);
        @SuppressWarnings("unused")
        LoginPresenter loginPresenter = new LoginPresenter(loginView, loginModel);


        primaryStage.setScene(loginView);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
