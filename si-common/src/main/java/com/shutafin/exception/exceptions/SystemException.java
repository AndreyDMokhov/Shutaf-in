package com.shutafin.exception.exceptions;


import com.shutafin.exception.AbstractAPIException;
import com.shutafin.model.web.error.ErrorType;

public class SystemException extends AbstractAPIException {

    @Override
    protected ErrorType getErrorType() {
        return ErrorType.SYSTEM;
    }


}
