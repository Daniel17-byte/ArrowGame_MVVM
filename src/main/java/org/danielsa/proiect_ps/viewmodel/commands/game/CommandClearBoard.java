package org.danielsa.proiect_ps.viewmodel.commands.game;

import eu.hansolo.tilesfx.Command;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.viewmodel.GameViewModel;

import java.io.File;

public class CommandClearBoard implements Command {
    private final GameViewModel viewModel;

    public CommandClearBoard(GameViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void execute() {
        if(viewModel.getBoardProperty().getValue() != null){
            viewModel.getModel().clearBoard();
            viewModel.getBoardProperty().getValue().getChildren().stream()
                    .filter(node -> node instanceof ImageView)
                    .map(node -> (ImageView) node)
                    .forEach(imageView -> imageView.setImage(new Image(new File(Main.path + "img.png").toURI().toString())));
        }
        if(viewModel.getLevelSelectChoiceBoxProperty().getValue().equals("4x4")){
            viewModel.getBoardProperty().setValue(viewModel.getGridSmallBoardProperty().getValue());
        }else {
            viewModel.getBoardProperty().setValue(viewModel.getGridLargeBoardProperty().getValue());
        }
    }
}
