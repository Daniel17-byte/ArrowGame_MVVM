package org.danielsa.proiect_ps.models;

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
    boolean checksExistingValidMoves();
}
