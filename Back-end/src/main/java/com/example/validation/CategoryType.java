package com.example.validation;

import com.example.validation.validators.CategoryValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Documented
@Constraint(validatedBy = CategoryValidator.class)
public @interface CategoryType {

    String message() default "{category.unsupported}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
