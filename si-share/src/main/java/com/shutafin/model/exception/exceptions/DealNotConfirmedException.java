package com.shutafin.model.exception.exceptions;

import com.shutafin.model.error.ErrorType;
import com.shutafin.model.exception.AbstractAPIException;

public class DealNotConfirmedException extends AbstractAPIException {

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DEAL_NOT_CONFIRMED_EXCEPTION;
    }
}
