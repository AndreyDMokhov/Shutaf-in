package com.shutafin.model.exception.exceptions;


import com.shutafin.model.exception.AbstractAPIException;
import com.shutafin.model.web.error.ErrorType;

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
