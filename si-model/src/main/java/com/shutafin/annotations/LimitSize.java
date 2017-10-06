package com.shutafin.annotations;

import com.shutafin.model.entities.types.ImageType;
import com.shutafin.validators.LimitSizeConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LimitSizeConstraintValidator.class)
public @interface LimitSize {

    ImageType value() default ImageType.PROFILE_IMAGE;

    String message() default "Invalid image size";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
