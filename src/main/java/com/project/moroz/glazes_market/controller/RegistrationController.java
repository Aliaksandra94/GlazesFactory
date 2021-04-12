package com.project.moroz.glazes_market.controller;

import com.project.moroz.glazes_market.entity.User;
import com.project.moroz.glazes_market.form.UserForm;
import com.project.moroz.glazes_market.model.Basket;
import com.project.moroz.glazes_market.service.UserServiceImpl;
import com.project.moroz.glazes_market.utils.Utils;
import com.project.moroz.glazes_market.validator.FormErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/registration")
@SessionAttributes("user")
public class RegistrationController {
    private UserServiceImpl userService;
    private MessageSource messageSource;
    private FormErrorMessage formErrorMessage;
    @ModelAttribute("user")
    public User newUser() {
        return new User();
    }
    @Autowired
    public void setFormErrorMessage(FormErrorMessage formErrorMessage) {
        this.formErrorMessage = formErrorMessage;
    }

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping
    public String showRegPage(HttpServletRequest request, Model model) {
        Basket basket = Utils.getBasketInSession(request);
        com.project.moroz.glazes_market.model.User userInfo = basket.getUserInfo();
        UserForm userForm = new UserForm(userInfo);
        model.addAttribute("user", userForm);
        return "authorization/registrationPage";
    }

    @PostMapping
    public String basketCustomerSave(HttpServletRequest request, Model model,
                                     @ModelAttribute("user") @Validated UserForm userForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            userForm.setValid(false);
            return "authorization/registrationPage";
        }
        String fieldNameError = formErrorMessage.checkFieldName(request.getParameter("name"));
        model.addAttribute("fieldNameError", fieldNameError);
        String fieldLoginError = formErrorMessage.checkFieldLogin(request.getParameter("login"));
        model.addAttribute("fieldLoginError", fieldLoginError);
        String fieldPassError = formErrorMessage.checkFieldPass(request.getParameter("password"));
        model.addAttribute("fieldPassError", fieldPassError);
        if (!formErrorMessage.checkFormValid(fieldNameError,fieldLoginError, fieldPassError)){
            return "authorization/registrationPage";
        }
        userService.registerNewUser(userForm, userForm.getLogin());
        Basket basket = Utils.getBasketInSession(request);
        com.project.moroz.glazes_market.model.User userInfo = new com.project.moroz.glazes_market.model.User(userForm);
        basket.setUserInfo(userInfo);
        return "redirect:/login";
    }
}