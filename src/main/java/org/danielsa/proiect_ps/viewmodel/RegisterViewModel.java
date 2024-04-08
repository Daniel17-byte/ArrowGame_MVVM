package org.danielsa.proiect_ps.viewmodel;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.Getter;
import org.danielsa.proiect_ps.model.RegisterModel;
import org.danielsa.proiect_ps.model.RegisterModelInterface;
import org.danielsa.proiect_ps.view.GameView;
import org.danielsa.proiect_ps.view.GameViewInterface;

@Getter
public class RegisterViewModel {
    private final RegisterModelInterface model;

    public RegisterViewModel() {
        this.model = new RegisterModel();
    }

    @SuppressWarnings("CastCanBeRemovedNarrowingVariableType")
    public void showRegisterResult(Label resultLabel, boolean success) {
        if (success) {
            GameViewInterface view = new GameView();
            Stage gameStage = new Stage();

            gameStage.setScene((GameView) view);
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
