package com.shutafin.model.exception.exceptions;

import com.shutafin.model.exception.AbstractAPIException;
import com.shutafin.model.web.error.ErrorType;

public class AccountBlockedException extends AbstractAPIException {

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ACCOUNT_BLOCKED;
    }
}
