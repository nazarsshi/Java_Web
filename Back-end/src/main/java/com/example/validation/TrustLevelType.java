package com.example.validation;

import com.example.validation.validators.TrustLevelValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Documented
@Constraint(validatedBy = TrustLevelValidator.class)
public @interface TrustLevelType {

    String message() default "{trust.level.unsupported}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
