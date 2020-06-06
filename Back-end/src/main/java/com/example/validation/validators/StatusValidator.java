package com.example.validation.validators;

import com.example.model.Status;
import com.example.validation.StatusType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StatusValidator implements ConstraintValidator<StatusType, String> {

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (name != null) {
            return Status.isStatus(name);
        }
        return true;
    }

}
