package com.shutafin.exception.exceptions;

import com.shutafin.exception.AbstractAPIException;
import com.shutafin.model.web.error.ErrorType;

public class ImageSizeLimitExceededException extends AbstractAPIException {

    public ImageSizeLimitExceededException() {
    }

    public ImageSizeLimitExceededException(String systemMessage) {
        super(systemMessage);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.IMAGE_SIZE_EXCEEDS_LIMIT;
    }
}
