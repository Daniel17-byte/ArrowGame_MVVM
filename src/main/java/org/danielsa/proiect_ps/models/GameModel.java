package org.danielsa.proiect_ps.models;

import lombok.Getter;

import java.util.Stack;

@Getter
public class GameModel implements GameModelInterface {
    private final ComputerPlayer computer;
    private final UserPlayer player;
    private GameBoardInterface board;
    private final Stack<Move> moveStack;

    public GameModel(ComputerPlayer computer, UserPlayer player, GameBoardInterface board) {
        this.computer = computer;
        this.player = player;
        this.board = board;
        this.moveStack = new Stack<>();
    }

    public boolean makeUserMove(Move move) {
        if (this.board.makeMove(move)) {
            moveStack.push(move);
            return true;
        }
        return false;
    }

    @Override
    public void changePlayerColor(UserPlayer player, String color) {
        if (!player.getColor().equals(color)) {
            computer.setColor(player.getColor());
            player.setColor(color);
        }
    }

    @Override
    public Move getSystemMove() {
        Move move = computer.makeMove(new GameBoardModel((GameBoardModel) (this.board)));
        if (move != null) {
            moveStack.push(move);
            board.makeMove(move);
            return move;
        }
        return null;
    }

    public Move undo() {
        if(moveStack.isEmpty()) return null;
        Move move = moveStack.pop();
        this.board.undoMove(move);
        return move;
    }

    @Override
    public UserPlayer getUserPlayer() {
        return this.player;
    }

    @Override
    public boolean isEndgame() {
        return this.board.noValidMoves() == 0;
    }

    @Override
    public void clearBoard() {
        this.board.clearBoard();
        moveStack.clear();
    }

    public void changeBoardSize(int size) {
        if(this.board.getSize() == size)
            return;
        this.board = new GameBoardModel(size);
    }

}
