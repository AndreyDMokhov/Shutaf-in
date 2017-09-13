package com.shutafin.handlers;

import com.shutafin.exception.AbstractAPIException;
import com.shutafin.model.web.error.ErrorResponse;
import com.shutafin.model.web.error.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler {

    @ExceptionHandler(ClientAbortException.class)
    public ResponseEntity getClientAbortResponse(ClientAbortException exception) {

        log.debug("ClientAbortException: ", exception);
        log.warn("ClientAbortException detected " + exception.getMessage());

        return new ResponseEntity(HttpStatus.GATEWAY_TIMEOUT);
    }
    @ExceptionHandler(AbstractAPIException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> getAPIExceptionResponse(AbstractAPIException exception) {

        exception.printStackTrace();

        HttpStatus httpStatus = HttpStatus.valueOf(exception.getErrorType().getHttpCode());
        return new ResponseEntity<>(exception.getErrorResponse(), httpStatus);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> getSystemExceptionResponse(Exception exception) {
        exception.printStackTrace();

        ErrorType errorType = ErrorType.SYSTEM;

        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), errorType);

        HttpStatus httpStatus = HttpStatus.valueOf(errorType.getHttpCode());

        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
