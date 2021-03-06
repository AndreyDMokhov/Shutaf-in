package com.shutafin.model.error;

import com.shutafin.model.exception.AbstractAPIException;
import com.shutafin.model.exception.exceptions.*;
import com.shutafin.model.exception.exceptions.validation.EmailNotUniqueValidationException;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;

public enum ErrorType {

    INPUT(400, ErrorCode.INP, InputValidationException.class),
    SYSTEM(500, ErrorCode.SYS, SystemException.class),
    AUTHENTICATION(401, ErrorCode.AUT, AuthenticationException.class),
    EMAIL_SEND_ERROR(503, ErrorCode.ESE, EmailSendException.class),
    EMAIL_CONFIGURATION_ERROR(500, ErrorCode.ECE, EmailNotificationProcessingException.class),
    EMAIL_DUPLICATION_EXCEPTION(400, ErrorCode.EDE, EmailNotUniqueValidationException.class),
    EMAIL_RESEND_INTERVAL_EXCEPTION(429, ErrorCode.ERI, EmailResendIntervalException.class),
    RESOURCE_NOT_FOUND_ERROR(404, ErrorCode.RNF, ResourceNotFoundException.class),
    INCORRECT_PASSWORD_ERROR(401, ErrorCode.IPE, IncorrectPasswordException.class),
    ACCOUNT_NOT_CONFIRMED(403, ErrorCode.ANC, AccountNotConfirmedException.class),
    ACCOUNT_BLOCKED(403, ErrorCode.ABL, AccountBlockedException.class),
    NO_ACCESS_PERMISSION(403, ErrorCode.NAP, NoPermissionException.class),
    LINK_ALREADY_CONFIRMED_EXCEPTION(400, ErrorCode.LAC, LinkAlreadyConfirmedException.class),
    LINK_EXPIRED_EXCEPTION(400, ErrorCode.LEE, LinkExpiredException.class),
    MULTIPLE_DEALS_ERROR(400, ErrorCode.MDE, MultipleDealsException.class),
    DEAL_NOT_CONFIRMED_EXCEPTION(403, ErrorCode.DNC, DealNotConfirmedException.class),
    DEAL_SELF_REMOVAL_EXCEPTION(400, ErrorCode.DSR, DealSelfRemovalException.class);

    private Integer httpCode;
    private ErrorCode errorCodeType;
    private Class<? extends AbstractAPIException> errorClass;

    ErrorType(Integer httpCode, ErrorCode errorCodeType, Class<? extends AbstractAPIException> errorClass) {
        this.httpCode = httpCode;
        this.errorCodeType = errorCodeType;
        this.errorClass = errorClass;
    }

    public String getErrorCodeType() {
        return errorCodeType.name();
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public Class<? extends AbstractAPIException> getErrorClass() {
        return errorClass;
    }

    public static ErrorType getById(String errorCodeName) {
        if (errorCodeName == null) {
            throw new IllegalArgumentException("Error code cannot be null");
        }

        for (ErrorType errorType : values()) {
            if (errorType.getErrorCodeType().equals(errorCodeName)) {
                return errorType;
            }
        }

        throw new IllegalArgumentException(String.format("Error type with error code %s does not exist", errorCodeName));
    }
}


enum ErrorCode {
    INP, SYS, AUT, ESE, ECE, EDE, RNF, IPE, ANC, ABL, NAP, ERI, MDE, LAC, LEE, DNC, DSR;
}