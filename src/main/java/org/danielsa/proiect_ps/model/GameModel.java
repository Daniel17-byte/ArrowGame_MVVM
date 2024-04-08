package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.DatabaseService;
import org.danielsa.proiect_ps.Main;

import java.util.ArrayList;
import java.util.Stack;

public class GameModel implements GameModelInterface {
    private final ComputerPlayer computer;
    private final UserPlayer player;
    private GameBoardInterface board;
    private final Stack<Move> moveStack;
    private final DatabaseService databaseService;

    public GameModel() {
        this.databaseService = Main.context.getBean(DatabaseService.class);
        this.computer = new ComputerPlayer("r");
        this.player = new UserPlayer("g");
        this.board = new GameBoardModel(8);
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
    public ComputerPlayer getComputer() {
        return this.computer;
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

    @Override
    public User getUser() {
        return databaseService.getUser();
    }

    @Override
    public void updateUserScore() {
        databaseService.updateUserScore();
    }

    @Override
    public ArrayList<User> getUsers() {
        return databaseService.getUsers();
    }
}
