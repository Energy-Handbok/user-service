package com.khaphp.userservice.util.validdata.gender;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidGenderValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGender {
    String message() default "Invalid Gender, must be MALE or FEMALE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
