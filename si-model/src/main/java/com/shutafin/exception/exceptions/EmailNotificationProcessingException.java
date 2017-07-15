package com.shutafin.exception.exceptions;

import com.shutafin.exception.AbstractAPIException;
import com.shutafin.model.web.error.ErrorType;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
public class EmailNotificationProcessingException extends AbstractAPIException {

    @Override
    public ErrorType getErrorType() {
        return ErrorType.EMAIL_CONFIGURATION_ERROR;
    }
}
