package com.asb.ms.brain.impl;

import com.asb.ms.brain.Game;
import com.asb.ms.consts.GameState;
import com.asb.ms.consts.Operation;
import com.asb.ms.exceptions.InvalidCellException;
import com.asb.ms.exceptions.InvalidGameException;
import com.asb.ms.exceptions.InvalidOperationException;
import com.asb.ms.model.Cell;
import com.asb.ms.model.GameData;
import com.asb.ms.parser.InputParser;
import com.asb.ms.parser.impl.InputParserImpl;
import com.asb.ms.validator.InputValidator;
import com.asb.ms.validator.impl.InputValidatorImpl;

/**
 * Implementation of Game.
 * Created by arjun on 23/04/16.
 */
public class GameImpl implements Game {

    private InputValidator inputValidator;
    private InputParser inputParser;
    private GameData gameData;

    public GameImpl(String gameDataString) throws InvalidGameException {
        inputValidator = new InputValidatorImpl();
        inputParser = new InputParserImpl();
        initialiseGame(gameDataString);
    }

    private void initialiseGame(String gameDataString) throws InvalidGameException {
        inputValidator.validateGame(gameDataString);
        gameData = new GameData(inputParser.parseGame(gameDataString));
    }

    @Override
    public void doOperation(String input) throws InvalidOperationException, InvalidCellException {
        inputValidator.validateOperation(input);
        Operation operation = inputParser.parseOperation(input);
        switch (operation) {
            case FLAG:
                this.flagCell(input);
                break;
            case OPEN:
                this.openCell(input);
                break;
        }
    }

    @Override
    public void flagCell(String input) throws InvalidCellException {
        inputValidator.validateCell(input, gameData);
        Cell cell = inputParser.parseCell(input);
        gameData.getCells()[cell.getX()][cell.getY()] = "f";
    }

    @Override
    public void openCell(String input) throws InvalidCellException {
        inputValidator.validateCell(input, gameData);
        Cell cell = inputParser.parseCell(input);
        if ("m".equals(gameData.getGame()[cell.getX()][cell.getY()])) {
            gameData.setCurrentState(GameState.LOST);
        } else {
            gameData.getCells()[cell.getX()][cell.getY()] = "0";
        }
    }

    @Override
    public GameState getGameState() {
        return gameData.getCurrentState();
    }

    @Override
    public boolean isOver() {
        if (0 == numberOfFreeCells()) {
            gameData.setCurrentState(GameState.WON);
        }
        return (gameData.getCurrentState() == GameState.LOST ||
                gameData.getCurrentState() == GameState.WON);
    }

    private int numberOfFreeCells() {
        int totalCells = getCount(gameData.getGame(), "x");
        int occupiedCells = getCount(gameData.getCells(), "0");
        return totalCells - occupiedCells;
    }

    private int getCount(String[][] data, String key) {
        int count = 0;
        for (String[] rows : data) {
            for (String cell : rows) {
                if (key.equals(cell)) {
                    count += 1;
                }
            }
        }
        return count;
    }

    @Override
    public String getGameData() {
        StringBuilder sb = new StringBuilder();
        int r = 0;
        for (String[] rows : this.gameData.getCells()) {
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
        return (r - 1) >= 0 && "m".equals(this.gameData.getGame()[r - 1][c]);
    }

    private boolean isBottomAMine(int r, int c) {
        return (r + 1) < this.gameData.getGame().length && "m".equals(this.gameData.getGame()[r + 1][c]);
    }

    private boolean isLeftAMine(int r, int c) {
        return (c - 1) >= 0 && "m".equals(this.gameData.getGame()[r][c - 1]);
    }

    private boolean isRightAMine(int r, int c) {
        return (c + 1) < this.gameData.getGame()[0].length && "m".equals(this.gameData.getGame()[r][c + 1]);
    }
}
