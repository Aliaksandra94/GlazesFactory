package com.project.moroz.glazes_market.controller;

import com.project.moroz.glazes_market.entity.User;
import com.project.moroz.glazes_market.form.UserForm;
import com.project.moroz.glazes_market.model.Basket;
import com.project.moroz.glazes_market.service.UserServiceImpl;
import com.project.moroz.glazes_market.service.interfaces.UserService;
import com.project.moroz.glazes_market.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
@SessionAttributes("user")
public class RegistrationController {
    private UserServiceImpl userService;

    @ModelAttribute("user")
    public User newUser() {
        return new User();
    }

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
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
    public String basketCustomerSave(HttpServletRequest request,Model model,
                                     //@ModelAttribute("user") @Valid User user,
                                     @ModelAttribute("user") @Validated UserForm userForm,
                                     BindingResult result,
                                     final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            userForm.setValid(false);
            return "authorization/registrationPage";
        }
        userService.registerNewUser(userForm, userForm.getLogin());
        Basket basket = Utils.getBasketInSession(request);
        com.project.moroz.glazes_market.model.User userInfo = new com.project.moroz.glazes_market.model.User(userForm);
        basket.setUserInfo(userInfo);
        return "redirect:/login";
    }
}