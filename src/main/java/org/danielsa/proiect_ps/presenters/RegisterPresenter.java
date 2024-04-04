package org.danielsa.proiect_ps.presenters;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.Getter;
import org.danielsa.proiect_ps.models.RegisterModel;
import org.danielsa.proiect_ps.models.RegisterModelInterface;
import org.danielsa.proiect_ps.views.GameView;
import org.danielsa.proiect_ps.views.GameViewInterface;
import org.danielsa.proiect_ps.views.RegisterViewInterface;
import org.danielsa.proiect_ps.views.RegisterView;

@Getter
public class RegisterPresenter {
    private final RegisterViewInterface view;
    private final RegisterModelInterface model;

    public RegisterPresenter(RegisterView view) {
        this.view = view;
        this.model = new RegisterModel(view.getDatabaseService());
    }

    @SuppressWarnings("CastCanBeRemovedNarrowingVariableType")
    public void showRegisterResult(Label resultLabel, boolean success) {
        if (success) {
            GameViewInterface view = new GameView(model.getDatabaseService());
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
