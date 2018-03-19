package com.shutafin.model.exception.exceptions;

import com.shutafin.model.error.ErrorType;
import com.shutafin.model.exception.AbstractAPIException;

public class MultipleDealsException extends AbstractAPIException {

    public MultipleDealsException(String errorMessage) {
        super(errorMessage);
    }

    public MultipleDealsException() {
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.MULTIPLE_DEALS_ERROR;
    }
}
