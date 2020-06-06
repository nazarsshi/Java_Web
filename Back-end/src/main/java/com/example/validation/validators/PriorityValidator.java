package com.example.validation.validators;

import com.example.model.Priority;
import com.example.validation.PriorityType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PriorityValidator implements ConstraintValidator<PriorityType, String> {

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (name != null) {
            return Priority.isPriority(name);
        }
        return true;
    }

}
