package org.danielsa.proiect_ps.presenters;

import org.danielsa.proiect_ps.models.*;

public class GamePresenter {
    private final GameViewInterface view;
    private final GameModelInterface model;

    public GamePresenter(GameViewInterface view) {
        this.view = view;
        model = new GameModel(new ComputerPlayer("r"), new UserPlayer("g"), new GameBoardModel(8));
        model.getComputer().setStrategy(new MinMaxStrategy(4, 10));
    }

    public void userRegisterMove(String direction, int row, int column) {
        boolean valid = model.makeUserMove(new Move(row, column, new Arrow(model.getUserPlayer().getColor(), direction)));

        if(!valid) {
            view.signalInvalidMove();
            return;
        }

        view.placeArrow(model.getUserPlayer().getColor(), direction, row, column);

        if(model.isEndgame()) {
            view.signalEndgame("User");
            return;
        }

        Move move = model.getSystemMove();
        if (move != null){
            view.placeArrow(model.getComputer().getColor(), move.getArrow().getDirection(), move.getX(), move.getY());
        }

        if(model.isEndgame()){
            view.signalEndgame("Computer");
        }
    }

    public void setPlayerColor(String color) {
        if(model.getUserPlayer().getColor().equals(color)) return;
        model.getComputer().setColor(model.getUserPlayer().getColor());
        model.getUserPlayer().setColor(color);
        model.changePlayerColor(model.getUserPlayer(), color);
    }

    public void changeLevel(int boardSize) {
        model.changeBoardSize(boardSize);
    }

    public void undoMove() {
        Move sysMove = model.undo();
        Move usrMove = model.undo();
        if(null != sysMove) view.removeArrow(sysMove.getX(), sysMove.getY());
        if(null != usrMove) view.removeArrow(usrMove.getX(), usrMove.getY());
    }

    public void clearBoard() {
        model.clearBoard();
    }
}
