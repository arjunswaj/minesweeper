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
import com.asb.ms.util.CellUtilImpl;
import com.asb.ms.util.impl.CellUtil;
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
    private CellUtil cellUtil;

    public GameImpl(String gameDataString) throws InvalidGameException {
        inputValidator = new InputValidatorImpl();
        inputParser = new InputParserImpl();
        cellUtil = new CellUtilImpl();
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
                        cellUtil.getTopCell(head),
                        cellUtil.getBottomCell(head),
                        cellUtil.getLeftCell(head),
                        cellUtil.getRightCell(head))
                        .stream()
                        .filter(c -> cellUtil.isValidCurrent(gameData.getCells(), c))
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
        count = cellUtil.checkTop(this.gameData.getGame(), MINE, r, c) ? count + 1 : count;
        count = cellUtil.checkBottom(this.gameData.getGame(), MINE, r, c) ? count + 1 : count;
        count = cellUtil.checkLeft(this.gameData.getGame(), MINE, r, c) ? count + 1 : count;
        count = cellUtil.checkRight(this.gameData.getGame(), MINE, r, c) ? count + 1 : count;
        return String.valueOf(count);
    }

}
