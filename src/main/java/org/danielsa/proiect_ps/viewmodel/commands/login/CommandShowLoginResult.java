package org.danielsa.proiect_ps.viewmodel.commands.login;

import eu.hansolo.tilesfx.Command;
import javafx.stage.Stage;
import org.danielsa.proiect_ps.view.GameView;
import org.danielsa.proiect_ps.viewmodel.LoginViewModel;

public class CommandShowLoginResult implements Command {
    private final LoginViewModel viewModel;

    public CommandShowLoginResult(LoginViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void execute() {
        boolean success = viewModel.getModel().authenticate(viewModel.getUsernameProperty().getValue(), viewModel.getPasswordProperty().getValue());

        if (success) {
            GameView view = new GameView();
            Stage gameStage = new Stage();

            gameStage.setScene(view);
            gameStage.setTitle("Arrow Game");
            gameStage.show();
        } else {
            viewModel.getResultLabelProperty().setValue("Login failed. Please try again.");
        }
    }
}
