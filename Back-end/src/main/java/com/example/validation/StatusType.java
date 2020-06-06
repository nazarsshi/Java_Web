package com.example.validation;

import com.example.validation.validators.StatusValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Documented
@Constraint(validatedBy = StatusValidator.class)
public @interface StatusType {

    String message() default "{status.unsupported}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
