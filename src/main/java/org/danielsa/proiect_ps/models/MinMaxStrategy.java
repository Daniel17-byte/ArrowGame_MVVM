package org.danielsa.proiect_ps.models;

import java.util.*;

public class MinMaxStrategy {
    private final int maxDepth;
    private final int maxMoves;

    public MinMaxStrategy(int maxDepth, int maxMoves) {
        this.maxDepth = maxDepth;
        this.maxMoves = maxMoves;
    }

    private int evaluationFunction(GameBoardInterface board) {
        return board.noValidMoves();
    }

    public Move makeMove(GameBoardInterface board) {
        Move bestMove = null;
        int max = 0;
        List<Move> randomMoves = getNElements(board.getValidMoves(), maxMoves);
        if (randomMoves.isEmpty()) {
            return null;
        }
        for(Move move : randomMoves) {
            board.makeMove(move);
            int val = maxValue(board, 0);
            if(val > max) {
                max = val;
                bestMove = move;
            }
            board.undoMove(move);
        }
        return bestMove;
    }

    private int minValue(GameBoardInterface board, int level) {
        if(level == maxDepth) return evaluationFunction(board);
        int min = 64;
        List<Move> randomMoves = getNElements(board.getValidMoves(), maxMoves);
        for(Move move : randomMoves) {
            board.makeMove(move);
            int val = maxValue(board, level + 1);
            if(val < min) min = val;
            board.undoMove(move);
        }
        return min;
    }

    private int maxValue(GameBoardInterface board, int level) {
        if(level == maxDepth) return evaluationFunction(board);
        int max = 0;
        List<Move> randomMoves = getNElements(board.getValidMoves(), maxMoves);
        for(Move move : randomMoves) {
            board.makeMove(move);
            int val = minValue(board, level + 1);
            if(val > max) max = val;
            board.undoMove(move);
        }
        return max;
    }

    private ArrayList<Move> getNElements(ArrayList<Move> source, int n) {
        Random rand = new Random();
        ArrayList<Move> destination = new ArrayList<>(n);
        ArrayList<Integer> indices = new ArrayList<>(source.size());

        for (int i = 0; i < source.size(); i++) {
            indices.add(i);
        }

        for (int i = 0; i < n; i++) {
            if (indices.isEmpty()) {
                break;
            }
            int randomIndex = rand.nextInt(indices.size());
            int sourceIndex = indices.get(randomIndex);
            destination.add(source.get(sourceIndex));
            indices.remove(randomIndex);
        }
        return destination;
    }

}
