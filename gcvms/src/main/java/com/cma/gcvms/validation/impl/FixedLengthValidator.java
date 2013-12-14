package com.cma.gcvms.validation.impl;

import static org.apache.commons.lang.StringUtils.isEmpty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cma.gcvms.validation.FixedLength;

public class FixedLengthValidator implements ConstraintValidator<FixedLength, String> {

    private FixedLength constraint;

    @Override
    public void initialize(FixedLength constraint) {
        this.constraint = constraint;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (isEmpty(value)) {
            return constraint.nullable();
        }
        return value.length() == constraint.length();
    }
}
