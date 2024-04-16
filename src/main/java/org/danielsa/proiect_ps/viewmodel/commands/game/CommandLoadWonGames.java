package org.danielsa.proiect_ps.viewmodel.commands.game;

import eu.hansolo.tilesfx.Command;
import org.danielsa.proiect_ps.model.User;
import org.danielsa.proiect_ps.model.UserType;
import org.danielsa.proiect_ps.viewmodel.GameViewModel;

public class CommandLoadWonGames implements Command {
    private final GameViewModel viewModel;

    public CommandLoadWonGames(GameViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void execute() {
        User user = viewModel.getModel().getUser();
        int gamesWon = user.getGamesWon();
        if (user.getUserType().equals(UserType.PLAYER)) {
            viewModel.getGameswonProperty().setValue("Games won : " + gamesWon);
        }else {
            viewModel.getGameswonProperty().setValue("Games won : " + gamesWon);
        }
    }
}
