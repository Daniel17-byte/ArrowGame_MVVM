package org.danielsa.proiect_ps.model;

import java.util.*;

public interface GameBoardInterface {
    ArrayList<String> smallBoardDirections = new ArrayList<>(Arrays.asList("N", "S", "E", "W"));
    ArrayList<String> largeBoardDirections = new ArrayList<>(Arrays.asList("N", "S", "E", "W", "NE", "NW", "SE", "SW"));

    int getSize();
    boolean makeMove(MoveModel moveModel);
    void undoMove(MoveModel moveModel);
    boolean isValidMove(MoveModel moveModel);
    int noValidMoves();
    ArrayList<MoveModel> getValidMoves();
    void clearBoard();
}
