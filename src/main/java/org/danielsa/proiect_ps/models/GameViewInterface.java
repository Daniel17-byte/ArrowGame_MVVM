package org.danielsa.proiect_ps.models;

public interface GameViewInterface {
    void placeArrow(String color, String direction, int row, int column);
    void removeArrow(int row, int column);
    void signalEndgame(String winner);
    void signalInvalidMove();
}
