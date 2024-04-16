package org.danielsa.proiect_ps.model;

import lombok.Setter;

@Setter
public class ComputerPlayerModelModel extends UserPlayerModel {
    private MinMaxStrategy strategy;

    public ComputerPlayerModelModel(String color) {
        super(color);
        this.strategy = new MinMaxStrategy(8,16);
    }

    public MoveModel makeMove(GameBoardInterface board) {
        return strategy.makeMove(board);
    }

}
