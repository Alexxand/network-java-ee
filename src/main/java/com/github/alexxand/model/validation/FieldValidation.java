package com.github.alexxand.model.validation;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FieldValidation {
    private final boolean isEmptyField;
    private final boolean isIncorrect;
    public boolean isInvalid(){
        return isIncorrect || isEmptyField;
    }
}
