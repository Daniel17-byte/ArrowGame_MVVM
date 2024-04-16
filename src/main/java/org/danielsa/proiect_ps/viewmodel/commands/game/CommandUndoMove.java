package org.danielsa.proiect_ps.viewmodel.commands.game;

import eu.hansolo.tilesfx.Command;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.model.MoveModel;
import org.danielsa.proiect_ps.viewmodel.GameViewModel;

import java.io.File;

public class CommandUndoMove implements Command {
    private final GameViewModel viewModel;

    public CommandUndoMove(GameViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void execute() {
        MoveModel sysMoveModel = viewModel.getModel().undo();
        MoveModel usrMoveModel = viewModel.getModel().undo();
        if(sysMoveModel != null) removeArrow(sysMoveModel.getX(), sysMoveModel.getY());
        if(usrMoveModel != null) removeArrow(usrMoveModel.getX(), usrMoveModel.getY());
    }

    private void removeArrow(int row, int column) {
        viewModel.getBoardProperty().getValue().getChildren().stream()
                .filter(node -> node instanceof ImageView)
                .map(node -> (ImageView) node)
                .filter(imageView -> {
                    Integer rowIdx = GridPane.getRowIndex(imageView);
                    Integer colIdx = GridPane.getColumnIndex(imageView);
                    return rowIdx != null && colIdx != null && rowIdx == row && colIdx == column;
                })
                .findFirst()
                .ifPresent(imageView -> imageView.setImage(new Image(new File(Main.path + "img.png").toURI().toString())));
    }
}
