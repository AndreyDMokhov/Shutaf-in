package com.shutafin.exception.exceptions;

import com.shutafin.exception.AbstractAPIException;
import com.shutafin.model.web.error.ErrorType;

/**
 * Created by Rogov on 06.08.2017.
 */
public class AccountBlockedException extends AbstractAPIException {

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ACCOUNT_BLOCKED;
    }
}
