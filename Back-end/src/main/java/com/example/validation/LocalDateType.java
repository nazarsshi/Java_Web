package com.example.validation;

import com.example.validation.validators.LocalDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Documented
@Constraint(validatedBy = LocalDateValidator.class)
public @interface LocalDateType {

    String message() default "{local.date.unsupported}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
