package com.furreverhome.Furrever_Home.validation;

import com.furreverhome.Furrever_Home.dto.user.PasswordDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final PasswordDto passwordDto = (PasswordDto) obj;
        return passwordDto.getNewPassword().equals(passwordDto.getVerifyNewPassword());
    }

}
