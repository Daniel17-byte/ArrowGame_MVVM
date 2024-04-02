package org.danielsa.proiect_ps.models;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class GameBoardModel implements GameBoardInterface {
    private final int size;
    private Arrow[][] board;
    private final ArrayList<String> directions;

    public GameBoardModel(int size) {
        this.size = size;
        if (size == 4) directions = smallBoardDirections;
        else directions = largeBoardDirections;
        board = new Arrow[size][size];
    }

    public GameBoardModel(GameBoardModel gameBoard) {
        this.size = gameBoard.getSize();
        this.directions = gameBoard.directions;
        this.board = new Arrow[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            System.arraycopy(gameBoard.board[i], 0, this.board[i], 0, this.size);
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    public boolean makeMove(Move move) {
        if (!isValidMove(move)) return false;
        int row = move.getX();
        int column = move.getY();
        board[row][column] = move.getArrow();
        return true;
    }

    public void undoMove(Move move) {
        int row = move.getX();
        int column = move.getY();
        board[row][column] = null;
    }

    public boolean isValidMove(Move move) {
        int row = move.getX();
        int column = move.getY();
        Arrow arrow = move.getArrow();
        if (null != board[row][column]) return false;
        for (int i = 0; i < size; i++) {

            if (arrow.equals(board[row][i]) && i != column) return false;
            if (arrow.equals(board[i][column]) && i != row) return false;

            int rmi = row - i - 1;
            int cmi = column - i - 1;
            int rpi = row + i + 1;
            int cpi = column + i + 1;

            if (rmi >= 0) {
                if (cpi < size) {
                    if (arrow.equals(board[rmi][cpi])) return false;
                }
                if (cmi >= 0) {
                    if (arrow.equals(board[rmi][cmi])) return false;
                }
            }
            if (rpi < size) {
                if (cpi < size) {
                    if (arrow.equals(board[rpi][cpi])) return false;
                }
                if (cmi >= 0) {
                    if (arrow.equals(board[rpi][cmi])) return false;
                }
            }
        }
        return true;
    }

    public ArrayList<Move> getValidMoves() {
        ArrayList<Move> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (null != board[i][j]) continue;
                for (String direction : directions) {
                    Move move = new Move(i, j, new Arrow(direction));
                    if (isValidMove(move)) {
                        result.add(move);
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
        this.board = new Arrow[size][size];
    }
}
