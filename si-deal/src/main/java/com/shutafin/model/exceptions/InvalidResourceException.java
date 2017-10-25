package com.shutafin.model.exceptions;

import com.shutafin.model.error.ErrorType;

public class InvalidResourceException extends AbstractAPIException {

    private static final String SYSTEM_MESSAGE = "File content differs from document type or file is corrupted";

    @Override
    public ErrorType getErrorType() {
        return ErrorType.INPUT;
    }

    public InvalidResourceException() {
        super(SYSTEM_MESSAGE);
    }
}
