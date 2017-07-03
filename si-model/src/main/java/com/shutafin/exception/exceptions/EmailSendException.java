package com.shutafin.exception.exceptions;

import com.shutafin.exception.AbstractAPIException;
import com.shutafin.model.web.error.ErrorType;

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
