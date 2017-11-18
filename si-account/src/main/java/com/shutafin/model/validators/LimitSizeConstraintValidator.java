package com.shutafin.model.validators;

import com.shutafin.model.annotations.LimitSize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class LimitSizeConstraintValidator implements ConstraintValidator<LimitSize, String> {

    private static final Integer BYTES_IN_KB = 1024;
    private static final Integer ENCODED_SYMBOL_NUMBER = 4;
    private static final Integer SOURCE_BYTES_NUMBER = 3;

    @Value("${image.size.limit}")
    private Integer imageSizeLimit;

    @Override
    public void initialize(LimitSize constraintAnnotation) {
        log.info("Initialized LimitSizeConstraintValidator");
    }

    @Override
    public boolean isValid(String image, ConstraintValidatorContext context) {
        if (image == null) {
            return false;
        }
        Integer imageSize = getImageSize(image);
        if (imageSize > imageSizeLimit) {
            log.error("Profile image size exceeds limit");
            log.error(String.format("Profile image size: %d KB, limit: %d KB", imageSize, imageSizeLimit));
            return false;
        }
        return true;
    }

    private Integer getImageSize(String image) {
        // According to Base64 encoding algorithm each 3 source bytes are encoded by 4 symbols
        return image.length() / ENCODED_SYMBOL_NUMBER * SOURCE_BYTES_NUMBER / BYTES_IN_KB;
    }
}
