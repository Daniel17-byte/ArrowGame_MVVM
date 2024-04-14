package org.danielsa.proiect_ps.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;
import lombok.Getter;
import org.danielsa.proiect_ps.model.RegisterModel;
import org.danielsa.proiect_ps.model.RegisterModelInterface;
import org.danielsa.proiect_ps.view.GameView;

public class RegisterViewModel {
    private final RegisterModelInterface model;
    @Getter
    private final StringProperty resultLabelProperty = new SimpleStringProperty();
    @Getter
    private final StringProperty usernameProperty = new SimpleStringProperty();
    @Getter
    private final StringProperty passwordProperty = new SimpleStringProperty();
    @Getter
    private final ObjectProperty<String> userTypeProperty = new SimpleObjectProperty<>();

    public RegisterViewModel() {
        this.model = new RegisterModel();
    }

    public void showRegisterResult() {
        boolean success = model.register(usernameProperty.getValue(), passwordProperty.getValue(), userTypeProperty.getValue());
        if (success) {
            GameView view = new GameView();
            Stage gameStage = new Stage();

            gameStage.setScene(view);
            gameStage.setTitle("Arrow Game");
            gameStage.show();
        } else {
            resultLabelProperty.setValue("Register failed. Please try again.");
        }
    }

}
