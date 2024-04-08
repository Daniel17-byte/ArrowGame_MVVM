package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.DatabaseService;

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
    DatabaseService getDatabaseService();
}
