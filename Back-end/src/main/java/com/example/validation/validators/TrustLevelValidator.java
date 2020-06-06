package com.example.validation.validators;

import com.example.model.TrustLevel;
import com.example.validation.TrustLevelType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TrustLevelValidator implements ConstraintValidator<TrustLevelType, String> {

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (name != null) {
            return TrustLevel.isTrustLevel(name);
        }
        return true;
    }

}
