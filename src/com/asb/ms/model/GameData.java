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
        String[][] game = new String[data.length][data[0].length];
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
        int r = 0;
        for (String[] rows : cells) {
            int c = 0;
            for (String cell : rows) {
                if ("m".equals(cell)) {
                    sb.append("x");
                } else {
                    if ("0".equals(cell)) {
                        sb.append(getHints(r, c));
                    } else {
                        sb.append(cell);
                    }
                }
                c += 1;
            }
            sb.append("\n");
            r += 1;
        }
        return sb.toString();
    }

    private String getHints(int r, int c) {
        int count = 0;
        count = isTopAMine(r, c) ? count + 1 : count;
        count = isBottomAMine(r, c) ? count + 1 : count;
        count = isLeftAMine(r, c) ? count + 1 : count;
        count = isRightAMine(r, c) ? count + 1 : count;
        return String.valueOf(count);
    }

    private boolean isTopAMine(int r, int c) {
        return (r - 1) >= 0 && "m".equals(this.game[r - 1][c]);
    }

    private boolean isBottomAMine(int r, int c) {
        return (r + 1) < this.game.length && "m".equals(this.game[r + 1][c]);
    }

    private boolean isLeftAMine(int r, int c) {
        return (c - 1) >= 0 && "m".equals(this.game[r][c - 1]);
    }

    private boolean isRightAMine(int r, int c) {
        return (c + 1) < this.game[0].length && "m".equals(this.game[r][c + 1]);
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
