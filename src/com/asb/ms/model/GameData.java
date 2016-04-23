package com.asb.ms.model;

import com.asb.ms.consts.GameState;

/**
 * Game Data.
 * Created by arjun on 23/04/16.
 */
public class GameData {
    private GameState currentState = GameState.NEW;
    private String[][] game;
    private String[][] cells;

    public GameData(String[][] data) {
        this.game = copy(data);
        this.cells = data;
        currentState = GameState.IN_PROGRESS;
    }

    private String[][] copy(String[][] data) {
        String[][] game = new String[data.length][data.length];
        for (int j = 0; j < data.length; j += 1) {
            String[] row = data[j];
            System.arraycopy(row, 0, game[j], 0, row.length);
        }
        return game;
    }

    public String[][] getCells() {
        return cells;
    }

    public void setCells(String[][] cells) {
        this.cells = cells;
    }

    public String[][] getGame() {
        return game;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }

    public String displayGame() {
        StringBuilder sb = new StringBuilder();
        for (String[] rows : cells) {
            for (String cell : rows) {
                if ("m".equals(cell)) {
                    sb.append("x");
                } else {
                    sb.append(cell);
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String[] rows : cells) {
            for (String cell : rows) {
                sb.append(cell);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
