package com.exponent.energy._game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Board implements Serializable {
    private final int size;
    private Tile[][] grid;
    private int score;
    private boolean won;
    private boolean gameOver;

    public Board(int size) {
        this.size = size;
        this.score = 0;
        this.won = false;
        this.gameOver = false;
        initGrid();
        spawnRandomTile();
        spawnRandomTile();
    }

    private void initGrid() {
        grid = new Tile[size][size];
        for (int r = 0; r < size; r++) for (int c = 0; c < size; c++) grid[r][c] = new Tile();
    }

    public int getSize() {
        return size;
    }

    public Tile[][] getGrid() {
        return grid;
    }

    public int getScore() {
        return score;
    }

    public boolean isWon() {
        return won;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private List<int[]> emptyCells() {
        List<int[]> empties = new ArrayList<>();
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++) if (grid[r][c].isEmpty()) empties.add(new int[]{r, c});
        return empties;
    }

    public void spawnRandomTile() {
        List<int[]> empties = emptyCells();
        if (empties.isEmpty()) return;
        int[] pos = empties.get(new Random().nextInt(empties.size()));
        int value = new Random().nextDouble() < 0.9 ? 2 : 4;
        grid[pos[0]][pos[1]].setValue(value);
    }

    private boolean canMove() {
        if (!emptyCells().isEmpty()) return true;
        // check neighbors for merge possibility
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++) {
                int v = grid[r][c].getValue();
                if (r + 1 < size && grid[r + 1][c].getValue().equals(v)) return true;
                if (c + 1 < size && grid[r][c + 1].getValue().equals(v)) return true;
            }
        return false;
    }

    // Functional style: moves are implemented by transforming rows/cols using helper functions
    public boolean moveLeft() {
        boolean changed = false;
        for (int r = 0; r < size; r++) {
            List<Integer> row = new ArrayList<>();
            for (int c = 0; c < size; c++) if (!grid[r][c].isEmpty()) row.add(grid[r][c].getValue());
            List<Integer> merged = mergeList(row);
            for (int c = 0; c < size; c++) {
                Integer v = c < merged.size() ? merged.get(c) : null;
                if ((grid[r][c].isEmpty() && v != null) || (!grid[r][c].isEmpty() && !Objects.equals(grid[r][c].getValue(), v))) {
                    changed = true;
                }
                grid[r][c].setValue(v);
            }
        }
        postMove(changed);
        return changed;
    }

    public boolean moveRight() {
        rotate180();
        boolean changed = moveLeft();
        rotate180();
        return changed;
    }

    public boolean moveUp() {
        rotateLeft();
        boolean changed = moveLeft();
        rotateRight();
        return changed;
    }

    public boolean moveDown() {
        rotateRight();
        boolean changed = moveLeft();
        rotateLeft();
        return changed;
    }

    private List<Integer> mergeList(List<Integer> items) {
        List<Integer> result = new ArrayList<>();
        int i = 0;
        while (i < items.size()) {
            if (i + 1 < items.size() && items.get(i).equals(items.get(i + 1))) {
                int merged = items.get(i) * 2;
                result.add(merged);
                score += merged;
                if (merged == 2048) won = true;
                i += 2;
            } else {
                result.add(items.get(i));
                i++;
            }
        }
        return result;
    }

    private void postMove(boolean changed) {
        if (changed) spawnRandomTile();
        gameOver = !canMove();
    }

    // rotation helpers
    private void rotateLeft() {
        Tile[][] tmp = new Tile[size][size];
        for (int r = 0; r < size; r++) for (int c = 0; c < size; c++) tmp[size - 1 - c][r] = grid[r][c];
        grid = tmp;
    }

    private void rotateRight() {
        Tile[][] tmp = new Tile[size][size];
        for (int r = 0; r < size; r++) for (int c = 0; c < size; c++) tmp[c][size - 1 - r] = grid[r][c];
        grid = tmp;
    }

    private void rotate180() {
        rotateLeft();
        rotateLeft();
    }
}