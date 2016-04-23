package com.asb.ms.validator.impl;

import com.asb.ms.exceptions.InvalidCellException;
import com.asb.ms.exceptions.InvalidGameException;
import com.asb.ms.exceptions.InvalidOperationException;
import com.asb.ms.model.GameData;
import com.asb.ms.validator.InputValidator;

/**
 * Input Validator Implementation.
 * Created by arjun on 23/04/16.
 */
public class InputValidatorImpl implements InputValidator {
    @Override
    public void validateGame(String gameData) throws InvalidGameException {

    }

    @Override
    public void validateOperation(String input) throws InvalidOperationException {

    }

    @Override
    public void validateCell(String input, GameData gameData) throws InvalidCellException {

    }
}
