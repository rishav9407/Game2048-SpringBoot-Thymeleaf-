package com.exponent.energy._game.service;


import com.exponent.energy._game.model.Board;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Value("${game.board.size:4}")
    private int defaultSize;

    // create new board (caller can store per-session)
    public Board createNew() {
        return new Board(defaultSize);
    }

    public void restart(Board board) {
        // create new board replacing fields (simple approach: new instance)
        Board newBoard = new Board(board.getSize());
        // reflect reference via caller (we'll return new board to the controller)
    }

    // apply move; returns true if board changed
    public boolean applyMove(Board board, String direction) {
        switch (direction.toUpperCase()) {
            case "LEFT":
                return board.moveLeft();
            case "RIGHT":
                return board.moveRight();
            case "UP":
                return board.moveUp();
            case "DOWN":
                return board.moveDown();
            default:
                return false;
        }
    }
}