package com.asb.ms.util;

import com.asb.ms.model.Cell;
import com.asb.ms.util.impl.CellUtil;

/**
 * Cell Util Impl.
 * Created by arjun on 23/04/16.
 */
public class CellUtilImpl implements CellUtil {
    @Override
    public boolean isValidCurrent(String[][] data, Cell cell) {
        return isValidCurrent(data, cell.getX(), cell.getY());
    }

    @Override
    public boolean isValidCurrent(String[][] data, int x, int y) {
        return x >= 0 && x < data[0].length && y >= 0 && y < data.length;
    }

    @Override
    public boolean checkTop(String[][] data, String key, int r, int c) {
        return (r - 1) >= 0 && key.equals(data[r - 1][c]);
    }

    @Override
    public boolean checkBottom(String[][] data, String key, int r, int c) {
        return (r + 1) < data.length && key.equals(data[r + 1][c]);
    }

    @Override
    public boolean checkLeft(String[][] data, String key, int r, int c) {
        return (c - 1) >= 0 && key.equals(data[r][c - 1]);
    }

    @Override
    public boolean checkRight(String[][] data, String key, int r, int c) {
        return (c + 1) < data[0].length && key.equals(data[r][c + 1]);
    }

    @Override
    public Cell getTopCell(Cell cell) {
        return this.getTopCell(cell.getX(), cell.getY());
    }

    @Override
    public Cell getTopCell(int r, int c) {
        return new Cell(r - 1, c);
    }

    @Override
    public Cell getBottomCell(int r, int c) {
        return new Cell(r + 1, c);
    }

    @Override
    public Cell getBottomCell(Cell cell) {
        return this.getBottomCell(cell.getX(), cell.getY());
    }

    @Override
    public Cell getLeftCell(int r, int c) {
        return new Cell(r, c - 1);
    }

    @Override
    public Cell getLeftCell(Cell cell) {
        return this.getLeftCell(cell.getX(), cell.getY());
    }

    @Override
    public Cell getRightCell(int r, int c) {
        return new Cell(r, c + 1);
    }

    @Override
    public Cell getRightCell(Cell cell) {
        return this.getRightCell(cell.getX(), cell.getY());
    }
}
