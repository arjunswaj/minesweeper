package com.asb.ms.parser.impl;

import com.asb.ms.consts.Operation;
import com.asb.ms.model.Cell;
import com.asb.ms.parser.InputParser;

import java.util.StringTokenizer;

/**
 * Input Parser Implementation.
 * Created by arjun on 23/04/16.
 */
public class InputParserImpl implements InputParser {

    @Override
    public String[][] parseGame(String gameDataString) {
        StringTokenizer st = new StringTokenizer(gameDataString, ",");
        String[][] gameData = new String[st.countTokens()][st.countTokens()];
        int row = 0;
        while (st.hasMoreTokens()) {
            String rowData = st.nextToken();
            int col = 0;
            for (char c : rowData.toCharArray()) {
                gameData[row][col] = String.valueOf(c);
                col += 1;
            }
            row += 1;
        }
        return gameData;
    }

    @Override
    public Cell parseCell(String input) {
        return new Cell(Integer.parseInt(String.valueOf(input.charAt(2))),
                Integer.parseInt(String.valueOf(input.charAt(4))));
    }

    @Override
    public Operation parseOperation(String input) {
        Operation operation = null;
        if (input.startsWith("f")) {
            operation = Operation.FLAG;
        } else if (input.startsWith("o")) {
            operation = Operation.OPEN;
        }
        return operation;
    }
}
