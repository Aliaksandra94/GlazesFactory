package com.project.moroz.glazes_market.entity.annotation;

import com.project.moroz.glazes_market.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueLoginValidation implements ConstraintValidator<UniqueLogin, String> {
    @Autowired
    private UserService userService;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && !userService.isLoginAlreadyInUse(s);
    }
}