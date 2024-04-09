package org.danielsa.proiect_ps.viewmodel;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.danielsa.proiect_ps.model.RegisterModel;
import org.danielsa.proiect_ps.model.RegisterModelInterface;
import org.danielsa.proiect_ps.view.GameView;

public class RegisterViewModel {
    private final RegisterModelInterface model;

    public RegisterViewModel() {
        this.model = new RegisterModel();
    }

    public void showRegisterResult(Label resultLabel, boolean success) {
        if (success) {
            GameView view = new GameView();
            Stage gameStage = new Stage();

            gameStage.setScene(view);
            gameStage.setTitle("Arrow Game");
            gameStage.show();
        } else {
            resultLabel.setText("Register failed. Please try again.");
        }
    }

    public boolean register(String username, String password, String userType) {
        return model.register(username, password, userType);
    }
}
