package com.shutafin.model.exception.exceptions;


import com.shutafin.model.error.ErrorType;
import com.shutafin.model.exception.AbstractAPIException;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
public class EmailSendException extends AbstractAPIException {

    @Override
    public ErrorType getErrorType() {
        return ErrorType.EMAIL_SEND_ERROR;
    }
}
