package com.shutafin.model.exception.exceptions;


import com.shutafin.model.error.ErrorType;
import com.shutafin.model.exception.AbstractAPIException;

public class SystemException extends AbstractAPIException {

    public SystemException(String systemMessage) {
        super(systemMessage);
    }
    public SystemException(){}

    @Override
    public ErrorType getErrorType() {
        return ErrorType.SYSTEM;
    }


}
