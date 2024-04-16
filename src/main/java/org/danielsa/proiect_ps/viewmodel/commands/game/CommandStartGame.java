package org.danielsa.proiect_ps.viewmodel.commands.game;

import eu.hansolo.tilesfx.Command;
import org.danielsa.proiect_ps.viewmodel.GameViewModel;

public class CommandStartGame implements Command {
    private final GameViewModel viewModel;

    public CommandStartGame(GameViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void execute() {
        viewModel.clearBoard();
        String color = "g";
        if(!viewModel.getModel().getUserPlayer().getColor().equals(color)) {
            viewModel.getModel().getComputer().setColor(viewModel.getModel().getUserPlayer().getColor());
            viewModel.getModel().getUserPlayer().setColor(color);
            viewModel.getModel().changePlayerColor(viewModel.getModel().getUserPlayer(), color);
        }

        String selectedBoard = viewModel.getLevelSelectChoiceBoxProperty().getValue();
        if(selectedBoard.equals("4x4")) {
            viewModel.getBoardProperty().setValue(viewModel.getGridSmallBoardProperty().getValue());
            viewModel.getModel().changeBoardSize(4);
            adjustInterButtons(false);
        } else {
            viewModel.getBoardProperty().setValue(viewModel.getGridLargeBoardProperty().getValue());
            viewModel.getModel().changeBoardSize(8);
            adjustInterButtons(true);
        }

    }

    private void adjustInterButtons(boolean isVisible) {
        viewModel.getButtonsProperty().getValue().get("nE").setVisible(isVisible);
        viewModel.getButtonsProperty().getValue().get("nW").setVisible(isVisible);
        viewModel.getButtonsProperty().getValue().get("sE").setVisible(isVisible);
        viewModel.getButtonsProperty().getValue().get("sW").setVisible(isVisible);
    }
}
