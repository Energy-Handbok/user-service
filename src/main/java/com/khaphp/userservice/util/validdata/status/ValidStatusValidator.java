package com.khaphp.userservice.util.validdata.status;

import com.khaphp.userservice.constant.Status;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidStatusValidator implements ConstraintValidator<ValidStatus, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> roles = List.of(Status.ACTIVE.toString(), Status.DEACTIVE.toString());
        if(roles.contains(value)){
            return true;
        }else{
            return false;
        }
    }
}
