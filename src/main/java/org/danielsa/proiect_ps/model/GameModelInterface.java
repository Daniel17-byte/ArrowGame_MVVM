package org.danielsa.proiect_ps.model;

import java.util.ArrayList;

public interface GameModelInterface {
    boolean makeUserMove(Move move);
    void changePlayerColor(UserPlayer player, String color);
    void changeBoardSize(int size);
    Move getSystemMove();
    Move undo();
    UserPlayer getUserPlayer();
    ComputerPlayer getComputer();
    boolean isEndgame();
    void clearBoard();
    void updateUserScore();
    User getUser();
    ArrayList<User> getUsers();
}
