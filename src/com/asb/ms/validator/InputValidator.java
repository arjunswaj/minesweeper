package com.asb.ms.validator;

import com.asb.ms.exceptions.InvalidCellException;
import com.asb.ms.exceptions.InvalidGameException;
import com.asb.ms.exceptions.InvalidOperationException;
import com.asb.ms.model.GameData;

/**
 * Input Validator.
 * Created by arjun on 23/04/16.
 */
public interface InputValidator {
    void validateGame(String gameData) throws InvalidGameException;

    void validateOperation(String input) throws InvalidOperationException;

    void validateCell(String input, GameData gameData) throws InvalidCellException;
}
