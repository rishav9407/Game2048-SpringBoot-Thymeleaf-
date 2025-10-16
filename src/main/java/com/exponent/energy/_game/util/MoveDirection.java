package com.exponent.energy._game.util;

public enum MoveDirection {
    UP, DOWN, LEFT, RIGHT;

    public static MoveDirection from(String s) {
        return MoveDirection.valueOf(s.toUpperCase());
    }
}