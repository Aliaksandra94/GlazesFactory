package com.project.moroz.glazes_market.entity.annotation;

import com.project.moroz.glazes_market.service.interfaces.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueLoginManagerValidation implements ConstraintValidator<UniqueLoginManager, String> {

    @Autowired
    private ManagerService managerService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && !managerService.isLoginAlreadyInUse(s);
    }
}
