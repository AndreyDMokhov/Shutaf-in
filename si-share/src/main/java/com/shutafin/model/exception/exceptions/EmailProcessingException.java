package com.shutafin.model.exception.exceptions;


import com.shutafin.model.error.ErrorType;
import com.shutafin.model.exception.AbstractAPIException;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
public class EmailProcessingException extends AbstractAPIException {

    @Override
    public ErrorType getErrorType() {
        return ErrorType.EMAIL_CONFIGURATION_ERROR;
    }
}
