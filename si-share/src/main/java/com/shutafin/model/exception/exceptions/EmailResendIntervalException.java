package com.shutafin.model.exception.exceptions;

import com.shutafin.model.error.ErrorType;
import com.shutafin.model.exception.AbstractAPIException;

public class EmailResendIntervalException extends AbstractAPIException {

    @Override
    public ErrorType getErrorType() {
        return ErrorType.EMAIL_RESEND_INTERVAL_EXCEPTION;
    }

}
