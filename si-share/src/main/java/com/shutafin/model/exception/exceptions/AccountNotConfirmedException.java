package com.shutafin.model.exception.exceptions;


import com.shutafin.model.error.ErrorType;
import com.shutafin.model.exception.AbstractAPIException;

/**
 * Created by Rogov on 06.08.2017.
 */
public class AccountNotConfirmedException extends AbstractAPIException {

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ACCOUNT_NOT_CONFIRMED;
    }
}
