package org.danielsa.proiect_ps.viewmodel.commands.game;

import eu.hansolo.tilesfx.Command;
import org.danielsa.proiect_ps.model.User;
import org.danielsa.proiect_ps.viewmodel.GameViewModel;

import java.util.ArrayList;

public class CommandLoadUsers implements Command {
    private final GameViewModel viewModel;

    public CommandLoadUsers(GameViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void execute() {
        ArrayList<User> users = viewModel.getModel().getUsers();
        StringBuilder stringBuilder = new StringBuilder();

        users.forEach( u -> stringBuilder.append(u).append("\n"));

        viewModel.getUsersPaneProperty().setValue(stringBuilder.toString());
    }
}
