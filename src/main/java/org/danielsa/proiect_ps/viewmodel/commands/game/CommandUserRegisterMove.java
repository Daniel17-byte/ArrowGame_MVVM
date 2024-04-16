package org.danielsa.proiect_ps.viewmodel.commands.game;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.model.ArrowModel;
import org.danielsa.proiect_ps.model.MoveModel;
import org.danielsa.proiect_ps.viewmodel.GameViewModel;

import java.io.File;

public class CommandUserRegisterMove {
    private final GameViewModel viewModel;

    public CommandUserRegisterMove(GameViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void execute(int row, int column) {
        boolean valid = viewModel.getModel().makeUserMove(new MoveModel(row, column, new ArrowModel(viewModel.getModel().getUserPlayer().getColor(), viewModel.getSelectedDirectionProperty().getValue())));

        if(!valid) {
            signalInvalidMove("Invalid Move.");
            return;
        }

        if(viewModel.getSelectedDirectionProperty().getValue() == null){
            signalInvalidMove("Direction not selected.");
            return;
        }

        placeArrow(viewModel.getModel().getUserPlayer().getColor(), viewModel.getSelectedDirectionProperty().getValue(), row, column);

        if(viewModel.getModel().isEndgame()) {
            signalEndgame("User");
            return;
        }

        MoveModel moveModel = viewModel.getModel().getSystemMove();
        if (moveModel != null){
            placeArrow(viewModel.getModel().getComputer().getColor(), moveModel.getArrowModel().getDirection(), moveModel.getX(), moveModel.getY());
        }

        if(viewModel.getModel().isEndgame()){
            signalEndgame("Computer");
        }
    }

    private void placeArrow(String color, String direction, int row, int column){
        Image image = new Image(new File(Main.path + color + direction + ".png").toURI().toString());

        viewModel.getBoardProperty().getValue().getChildren().stream()
                .filter(node -> node instanceof ImageView)
                .map(node -> (ImageView) node)
                .filter(imageView -> {
                    Integer rowIdx = GridPane.getRowIndex(imageView);
                    Integer colIdx = GridPane.getColumnIndex(imageView);
                    return rowIdx != null && colIdx != null && rowIdx == row && colIdx == column;
                })
                .findFirst()
                .ifPresent(imageView -> imageView.setImage(image));
    }

    private void signalInvalidMove(String message) {
        final Stage dialog = new Stage();
        dialog.setTitle("Error.");
        dialog.setX(950);
        dialog.setY(300);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.centerOnScreen();
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(new Text(message));
        Scene dialogScene = new Scene(dialogVbox, 150, 100);
        dialogVbox.setOnMouseClicked(mouseEvent -> dialog.close());
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void signalEndgame(String winner) {
        final Stage dialog = new Stage();
        dialog.setTitle("Game end.");
        dialog.setX(950);
        dialog.setY(300);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.centerOnScreen();
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        if(winner.equals("User")){
            winner = viewModel.getModel().getUser().getUserName();
        }
        dialogVbox.getChildren().add(new Text(winner.toUpperCase() + " wins!"));
        Scene dialogScene = new Scene(dialogVbox, 150, 100);
        dialogVbox.setOnMouseClicked(e -> {
            dialog.close();
            viewModel.clearBoard();
        });
        dialog.setScene(dialogScene);
        dialog.show();
        if (!winner.equalsIgnoreCase("COMPUTER")){
            viewModel.getModel().updateUserScore();
        }
        viewModel.loadWonGames();
    }
}
