package com.example.validation;

import com.example.validation.validators.PriorityValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Documented
@Constraint(validatedBy = PriorityValidator.class)
public @interface PriorityType {

    String message() default "{priority.unsupported}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
