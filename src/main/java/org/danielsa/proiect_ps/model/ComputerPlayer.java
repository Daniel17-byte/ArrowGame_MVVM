package org.danielsa.proiect_ps.model;

import lombok.Setter;

@Setter
public class ComputerPlayer extends UserPlayer {
    private MinMaxStrategy strategy;

    public ComputerPlayer(String color) {
        super(color);
        this.strategy = new MinMaxStrategy(8,16);
    }

    public Move makeMove(GameBoardInterface board) {
        return strategy.makeMove(board);
    }

}
