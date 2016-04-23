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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

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
        gameData.getCells()[cell.getX()][cell.getY()] = FLAG;
    }

    @Override
    public void openCell(String input) throws InvalidCellException {
        inputValidator.validateCell(input, gameData);
        Cell cell = inputParser.parseCell(input);
        if (MINE.equals(gameData.getGame()[cell.getX()][cell.getY()])) {
            gameData.setCurrentState(GameState.LOST);
        } else {
            Queue<Cell> queue = new LinkedList<>();
            queue.add(cell);
            while (!queue.isEmpty()) {
                Cell head = queue.remove();
                gameData.getCells()[head.getX()][head.getY()] = ZERO;
                queue.addAll(Arrays.asList(
                        this.getTopCell(head),
                        this.getBottomCell(head),
                        this.getLeftCell(head),
                        this.getRightCell(head))
                        .stream()
                        .filter(c -> isValidCurrent(gameData.getCells(), c))
                        .filter(c -> !MINE.equals(gameData.getCells()[c.getX()][c.getY()]))
                        .filter(c -> ZERO.equals(getHints(c)))
                        .filter(c -> !gameData.getCells()[c.getX()][c.getY()].equals(ZERO))
                        .collect(Collectors.toList()));
            }
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
        int totalCells = getCount(gameData.getGame(), FREE_CELL);
        int occupiedCells = getCount(gameData.getCells(), ZERO);
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
                if (MINE.equals(cell)) {
                    sb.append(FREE_CELL);
                } else {
                    if (ZERO.equals(cell)) {
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

    private String getHints(Cell cell) {
        return this.getHints(cell.getX(), cell.getY());
    }

    private String getHints(int r, int c) {
        int count = 0;
        count = checkTop(this.gameData.getGame(), MINE, r, c) ? count + 1 : count;
        count = checkBottom(this.gameData.getGame(), MINE, r, c) ? count + 1 : count;
        count = checkLeft(this.gameData.getGame(), MINE, r, c) ? count + 1 : count;
        count = checkRight(this.gameData.getGame(), MINE, r, c) ? count + 1 : count;
        return String.valueOf(count);
    }

    private boolean isValidCurrent(String[][] data, Cell cell) {
        return isValidCurrent(data, cell.getX(), cell.getY());
    }

    private boolean isValidCurrent(String[][] data, int x, int y) {
        return x >= 0 && x < data[0].length && y >= 0 && y < data.length;
    }

    private boolean checkTop(String[][] data, String key, int r, int c) {
        return (r - 1) >= 0 && key.equals(data[r - 1][c]);
    }

    private boolean checkBottom(String[][] data, String key, int r, int c) {
        return (r + 1) < data.length && key.equals(data[r + 1][c]);
    }

    private boolean checkLeft(String[][] data, String key, int r, int c) {
        return (c - 1) >= 0 && key.equals(data[r][c - 1]);
    }

    private boolean checkRight(String[][] data, String key, int r, int c) {
        return (c + 1) < data[0].length && key.equals(data[r][c + 1]);
    }

    private Cell getTopCell(Cell cell) {
        return this.getTopCell(cell.getX(), cell.getY());
    }

    private Cell getTopCell(int r, int c) {
        return new Cell(r - 1, c);
    }

    private Cell getBottomCell(int r, int c) {
        return new Cell(r + 1, c);
    }

    private Cell getBottomCell(Cell cell) {
        return this.getBottomCell(cell.getX(), cell.getY());
    }

    private Cell getLeftCell(int r, int c) {
        return new Cell(r, c - 1);
    }

    private Cell getLeftCell(Cell cell) {
        return this.getLeftCell(cell.getX(), cell.getY());
    }

    private Cell getRightCell(int r, int c) {
        return new Cell(r, c + 1);
    }

    private Cell getRightCell(Cell cell) {
        return this.getRightCell(cell.getX(), cell.getY());
    }
}
