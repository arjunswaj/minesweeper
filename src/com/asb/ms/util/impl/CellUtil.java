package com.asb.ms.util.impl;

import com.asb.ms.model.Cell;

/**
 * Created by arjun on 23/04/16.
 */
public interface CellUtil {
    boolean isValidCurrent(String[][] data, Cell cell);

    boolean isValidCurrent(String[][] data, int x, int y);

    boolean checkTop(String[][] data, String key, int r, int c);

    boolean checkBottom(String[][] data, String key, int r, int c);

    boolean checkLeft(String[][] data, String key, int r, int c);

    boolean checkRight(String[][] data, String key, int r, int c);

    Cell getTopCell(Cell cell);

    Cell getTopCell(int r, int c);

    Cell getBottomCell(int r, int c);

    Cell getBottomCell(Cell cell);

    Cell getLeftCell(int r, int c);

    Cell getLeftCell(Cell cell);

    Cell getRightCell(int r, int c);

    Cell getRightCell(Cell cell);
}
