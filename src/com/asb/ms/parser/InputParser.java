package com.asb.ms.parser;

import com.asb.ms.consts.Operation;
import com.asb.ms.model.Cell;

/**
 * Input Parser.
 * Created by arjun on 23/04/16.
 */
public interface InputParser {
    String[][] parseGame(String gameDataString);

    Cell parseCell(String input);

    Operation parseOperation(String input);
}
