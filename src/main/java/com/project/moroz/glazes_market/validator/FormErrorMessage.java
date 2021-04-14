package com.project.moroz.glazes_market.validator;

import com.project.moroz.glazes_market.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FormErrorMessage {
    private UserService userService;
    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String checkFieldName(String parameter) {
        String errorMessage = null;
        if (parameter.equals("") || parameter == null) {
            errorMessage = messageSource.getMessage("error.emptyUserName", new Object[]{"error.emptyUserName"}, LocaleContextHolder.getLocale());
        } else if (parameter.toCharArray().length <= 2 || parameter.toCharArray().length >= 15) {
            errorMessage = messageSource.getMessage("error.sizeUserName", new Object[]{"error.sizeUserName"}, LocaleContextHolder.getLocale());
        }
        return errorMessage;
    }

    public String checkFieldLogin(String parameter) {
        String errorMessage = null;
        if (parameter.equals("") || parameter == null) {
            errorMessage = messageSource.getMessage("error.emptyUserLogin", new Object[]{"error.emptyUserLogin"}, LocaleContextHolder.getLocale());
        } else if (parameter.toCharArray().length <= 2 || parameter.toCharArray().length >= 15) {
            errorMessage = messageSource.getMessage("error.sizeUserLogin", new Object[]{"error.sizeUserLogin"}, LocaleContextHolder.getLocale());
        }
        //        else if (!userService.isUsernameUnique(parameter)){
//            errorMessage = messageSource.getMessage("duplicate.customerForm.name", new Object[]{"duplicate.customerForm.name"}, LocaleContextHolder.getLocale());
//        }
        return errorMessage;
    }

    public String checkFieldPass(String parameter) {
        String errorMessage = null;
        if (parameter.equals("") || parameter == null) {
            errorMessage = messageSource.getMessage("error.emptyUserPass", new Object[]{"error.emptyUserPass"}, LocaleContextHolder.getLocale());
        } else if (parameter.toCharArray().length <= 4 || parameter.toCharArray().length >= 11) {
            errorMessage = messageSource.getMessage("error.sizeUserPass", new Object[]{"error.sizeUserPass"}, LocaleContextHolder.getLocale());
        }
        return errorMessage;
    }

    public String checkFieldPassForPersonalEdit(String parameter) {
        String errorMessage = null;
        if (parameter.equals("") || parameter == null) {
            errorMessage = messageSource.getMessage("error.emptyUserPass", new Object[]{"error.emptyUserPass"}, LocaleContextHolder.getLocale());
        } else if (parameter.toCharArray().length <= 4) {
            errorMessage = messageSource.getMessage("error.sizeUserPass", new Object[]{"error.sizeUserPass"}, LocaleContextHolder.getLocale());
        }
        return errorMessage;
    }

    public String checkFieldDiscount(String parameter, double number) {
        String errorMessage = null;
        if (parameter.equals("") || parameter == null) {
            errorMessage = messageSource.getMessage("error.emptyDiscount", new Object[]{"error.emptyDiscount"}, LocaleContextHolder.getLocale());
        } else if (number <= 0) {
            errorMessage = messageSource.getMessage("error.wrongDoubleType", new Object[]{"error.wrongDoubleType"}, LocaleContextHolder.getLocale());
        }
        return errorMessage;
    }

    public boolean checkFormValid(String error1, String error2, String error3) {
        boolean isValid;
        if ((error1 == null || error1.equals("")) && (error2 == null || error2.equals("")) && (error3 == null || error3.equals(""))) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    public boolean checkAddUserFormValid(String error1, String error2, String error3, String error4) {
        boolean isValid;
        if ((error1 == null || error1.equals("")) && (error2 == null || error2.equals("")) && (error3 == null || error3.equals(""))
                && (error4 == null || error4.equals(""))) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    public String checkFieldProductName(String parameter) {
        String errorMessage = null;
        if (parameter.equals("") || parameter == null) {
            errorMessage = messageSource.getMessage("error.emptyProductName", new Object[]{"error.emptyProductName"}, LocaleContextHolder.getLocale());
        } else if (parameter.toCharArray().length <= 4 || parameter.toCharArray().length >= 15) {
            errorMessage = messageSource.getMessage("error.sizeProductName", new Object[]{"error.sizeProductName"}, LocaleContextHolder.getLocale());
        }
        return errorMessage;
    }

    public String checkFieldProductQuantity(String parameter, int number) {
        String errorMessage = null;
        if (parameter.equals("") || parameter == null) {
            errorMessage = messageSource.getMessage("error.emptyQuantity", new Object[]{"error.emptyQuantity"}, LocaleContextHolder.getLocale());
        } else if (number <= 0) {
            errorMessage = messageSource.getMessage("error.wrongType", new Object[]{"error.wrongType"}, LocaleContextHolder.getLocale());
        }
        return errorMessage;
    }

    public boolean checkCreateProductFormValid(String error1, String error2) {
        boolean isValid;
        if ((error1 == null || error1.equals("")) && (error2 == null || error2.equals(""))) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

}
