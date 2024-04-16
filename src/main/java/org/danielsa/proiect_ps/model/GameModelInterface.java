package org.danielsa.proiect_ps.model;

import java.util.ArrayList;

public interface GameModelInterface {
    boolean makeUserMove(MoveModel moveModel);
    void changePlayerColor(UserPlayerModel player, String color);
    void changeBoardSize(int size);
    MoveModel getSystemMove();
    MoveModel undo();
    UserPlayerModel getUserPlayer();
    ComputerPlayerModelModel getComputer();
    boolean isEndgame();
    void clearBoard();
    void updateUserScore();
    UserModel getUser();
    ArrayList<UserModel> getUsers();
}
