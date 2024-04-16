package org.danielsa.proiect_ps.viewmodel.commands.game;

import org.danielsa.proiect_ps.model.UserModel;
import org.danielsa.proiect_ps.viewmodel.GameViewModel;

public class CommandGetUser {
    private final GameViewModel viewModel;

    public CommandGetUser(GameViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public UserModel execute() {
        return viewModel.getModel().getUser();
    }
}
