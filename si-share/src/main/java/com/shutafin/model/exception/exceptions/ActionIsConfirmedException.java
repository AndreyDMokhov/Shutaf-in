package com.shutafin.model.exception.exceptions;

import com.shutafin.model.error.ErrorType;
import com.shutafin.model.exception.AbstractAPIException;

/**
 * Created by evgeny on 3/27/2018.
 */
public class ActionIsConfirmedException extends AbstractAPIException {
    @Override
    public ErrorType getErrorType() {
        return ErrorType.ACTION_IS_CONFIRMED_EXCEPTION;
    }
}
