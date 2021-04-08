package com.project.moroz.glazes_market.validator;

import com.project.moroz.glazes_market.form.UserForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

    }

    @Component
    public class CustomerFormValidator implements Validator {

        @Override
        public boolean supports(Class<?> clazz) {
            return clazz == UserForm.class;
        }

        @Override
        public void validate(Object target, Errors errors) {
            UserForm userInfo = (UserForm) target;

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.customerForm.name");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "NotEmpty.customerForm.login");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.customerForm.password");

        }

    }
}
