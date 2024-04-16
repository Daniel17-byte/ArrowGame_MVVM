package org.danielsa.proiect_ps.viewmodel.commands.game;

import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import org.danielsa.proiect_ps.Main;
import org.danielsa.proiect_ps.viewmodel.GameViewModel;

import java.io.File;

public class CommandInitBoard {
    private final GameViewModel viewModel;

    public CommandInitBoard(GameViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public GridPane execute(String sizeS){
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(500, 500);

        int size = 4;

        if (sizeS.equals("large")){
            size = 8;
        }

        for (int i = 0; i < size; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / size);
            gridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < size; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / size);
            gridPane.getRowConstraints().add(row);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ImageView imageView = new ImageView(new File(Main.path + "img.png").toURI().toString());
                imageView.setOnMouseClicked(this::clickedBoard);
                imageView.setFitWidth(41.0);
                imageView.setFitHeight(38.0);
                GridPane.setMargin(imageView, new Insets(2));
                gridPane.add(imageView, j, i);
            }
        }

        return gridPane;
    }

    private void clickedBoard(MouseEvent mouseEvent){
        EventTarget source = mouseEvent.getTarget();
        if(!(source instanceof ImageView)){
            return;
        }
        ImageView selectedImage = (ImageView)source;
        int row = GridPane.getRowIndex(selectedImage);
        int col = GridPane.getColumnIndex(selectedImage);
        viewModel.userRegisterMove(row, col);
    }
}
