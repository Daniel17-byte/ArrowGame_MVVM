package org.danielsa.proiect_ps.model;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class GameBoardModel implements GameBoardInterface {
    private final int size;
    private ArrowModel[][] board;
    private final ArrayList<String> directions;

    public GameBoardModel(int size) {
        this.size = size;
        if (size == 4) directions = smallBoardDirections;
        else directions = largeBoardDirections;
        board = new ArrowModel[size][size];
    }

    public GameBoardModel(GameBoardModel gameBoard) {
        this.size = gameBoard.getSize();
        this.directions = gameBoard.directions;
        this.board = new ArrowModel[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            System.arraycopy(gameBoard.board[i], 0, this.board[i], 0, this.size);
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    public boolean makeMove(MoveModel moveModel) {
        if (!isValidMove(moveModel)) return false;
        int row = moveModel.getX();
        int column = moveModel.getY();
        board[row][column] = moveModel.getArrowModel();
        return true;
    }

    public void undoMove(MoveModel moveModel) {
        int row = moveModel.getX();
        int column = moveModel.getY();
        board[row][column] = null;
    }

    public boolean isValidMove(MoveModel moveModel) {
        int row = moveModel.getX();
        int column = moveModel.getY();
        ArrowModel arrowModel = moveModel.getArrowModel();
        if (null != board[row][column]) return false;
        for (int i = 0; i < size; i++) {

            if (arrowModel.equals(board[row][i]) && i != column) return false;
            if (arrowModel.equals(board[i][column]) && i != row) return false;

            int rmi = row - i - 1;
            int cmi = column - i - 1;
            int rpi = row + i + 1;
            int cpi = column + i + 1;

            if (rmi >= 0) {
                if (cpi < size) {
                    if (arrowModel.equals(board[rmi][cpi])) return false;
                }
                if (cmi >= 0) {
                    if (arrowModel.equals(board[rmi][cmi])) return false;
                }
            }
            if (rpi < size) {
                if (cpi < size) {
                    if (arrowModel.equals(board[rpi][cpi])) return false;
                }
                if (cmi >= 0) {
                    if (arrowModel.equals(board[rpi][cmi])) return false;
                }
            }
        }
        return true;
    }

    public ArrayList<MoveModel> getValidMoves() {
        ArrayList<MoveModel> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (null != board[i][j]) continue;
                for (String direction : directions) {
                    MoveModel moveModel = new MoveModel(i, j, new ArrowModel(direction));
                    if (isValidMove(moveModel)) {
                        result.add(moveModel);
                        break;
                    }
                }
            }
        }
        return result;
    }

    public int noValidMoves() {
        return getValidMoves().size();
    }

    @Override
    public void clearBoard() {
        this.board = new ArrowModel[size][size];
    }
}
