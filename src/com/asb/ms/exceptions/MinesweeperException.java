package com.asb.ms.exceptions;

/**
 * Minesweeper Exception.
 * Created by arjun on 23/04/16.
 */
public abstract class MinesweeperException extends Exception {
    public MinesweeperException(String message) {
        super(message);
    }
}
