package org.danielsa.proiect_ps.view;

import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import org.danielsa.proiect_ps.DatabaseService;

public interface GameViewInterface {
    DatabaseService getDatabaseService();
    void setSelectedDirection(String text);
    String getSelectedDirection();
    void setBoard(GridPane board);
    GridPane getBoard();
    void setSmallBoard(GridPane smallBoard);
    void setLargeBoard(GridPane smallBoard);
    GridPane getSmallBoard();
    GridPane getLargeBoard();
    ChoiceBox<String> getLevelSelectChoiceBox();
}
