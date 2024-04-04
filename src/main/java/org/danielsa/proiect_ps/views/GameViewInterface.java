package org.danielsa.proiect_ps.views;

import javafx.scene.input.MouseEvent;

public interface GameViewInterface {
    void placeArrow(String color, String direction, int row, int column);
    void removeArrow(int row, int column);
    void signalEndgame(String winner);
    void signalInvalidMove();
    void clickedUndoButton(MouseEvent mouseEvent);
    void clickedRestartButton(MouseEvent mouseEvent);
    void clickedStartGame(MouseEvent mouseEvent);
    void clickedManageUsersButton(MouseEvent mouseEvent);
    void loadUserList();
    void loadWonGames();
}
