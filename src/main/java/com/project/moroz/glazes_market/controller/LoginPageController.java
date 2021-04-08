package com.project.moroz.glazes_market.controller;

import com.project.moroz.glazes_market.entity.User;
import com.project.moroz.glazes_market.form.UserForm;
import com.project.moroz.glazes_market.model.Basket;
import com.project.moroz.glazes_market.service.UserServiceImpl;
import com.project.moroz.glazes_market.service.interfaces.UserService;
import com.project.moroz.glazes_market.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/login")
@SessionAttributes("user")
public class LoginPageController {
    private UserServiceImpl userService;
    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public User newUser() {
        return new User();
    }

    @GetMapping
    public String showLoginPage(HttpServletRequest request, Model model) {
        Basket basket = Utils.getBasketInSession(request);
        com.project.moroz.glazes_market.model.User userInfo = basket.getUserInfo();
        UserForm userForm = new UserForm(userInfo);
        model.addAttribute("user", userForm);
        return "authorization/loginPage";
    }

    @PostMapping
    public String basketCustomerSave(HttpServletRequest request, //
                                     Model model, //
                                     @ModelAttribute("user") @Validated UserForm userForm, //
                                     BindingResult result, //
                                     final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            userForm.setValid(false);
            return "authorization/loginPage";
        }
        userForm.setValid(true);
        Basket basket = Utils.getBasketInSession(request);
        com.project.moroz.glazes_market.model.User userInfo = new com.project.moroz.glazes_market.model.User(userForm);
        basket.setUserInfo(userInfo);
        return "redirect:/userPage";
    }

    @GetMapping("/failedEnter")
    public String failedEnter(Model model) {
        String errorMessage = messageSource.getMessage("error.authorization", new Object[]{"error.authorization"}, LocaleContextHolder.getLocale());
        model.addAttribute("msg", errorMessage);
        return "authorization/loginPage";
    }

    @GetMapping("/successEnter")
    public String successEnter(Model model, Principal principal) {
        String login = principal.getName();
        User user = (User) userService.returnUserByLogin(login);
        model.addAttribute("user", user);
        return "redirect:/userPage";
    }

    @PostMapping(value = "/logout")
    public String logout() {
        return "authorization/loginPage";
    }

}
