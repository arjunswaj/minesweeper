package com.asb.ms.brain;

import com.asb.ms.consts.GameState;
import com.asb.ms.exceptions.InvalidCellException;
import com.asb.ms.exceptions.InvalidOperationException;

/**
 * Minesweeper board.
 * Created by arjun on 23/04/16.
 */
public interface Game {

    String MINE = "m";
    String FLAG = "f";
    String OPEN = "0";
    String FREE_CELL = "x";

    void doOperation(String input) throws InvalidOperationException, InvalidCellException;

    void flagCell(String input) throws InvalidCellException;

    void openCell(String input) throws InvalidCellException;

    GameState getGameState();

    boolean isOver();

    String getGameData();

}
