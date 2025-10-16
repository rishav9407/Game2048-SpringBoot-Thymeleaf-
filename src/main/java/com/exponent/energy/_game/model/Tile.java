package com.exponent.energy._game.model;

import java.io.Serializable;

public class Tile implements Serializable {
    private Integer value;

    public Tile() {
        this.value = null;
    }

    public Tile(Integer v) {
        this.value = v;
    }

    public boolean isEmpty() {
        return value == null;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void clear() {
        this.value = null;
    }
}