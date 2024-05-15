package com.khaphp.userservice.util.validdata.gender;

import com.khaphp.userservice.constant.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidGenderValidator implements ConstraintValidator<ValidGender, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> genders = List.of(Gender.MALE.toString(), Gender.FEMALE.toString());
        if(genders.contains(value)){
            return true;
        }else{
            return false;
        }
    }
}
