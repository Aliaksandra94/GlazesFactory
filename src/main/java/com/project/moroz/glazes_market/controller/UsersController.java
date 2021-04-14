package com.project.moroz.glazes_market.controller;

import com.project.moroz.glazes_market.entity.*;
import com.project.moroz.glazes_market.service.interfaces.ManagerService;
import com.project.moroz.glazes_market.service.interfaces.SolvencyService;
import com.project.moroz.glazes_market.service.interfaces.UserService;
import com.project.moroz.glazes_market.utils.Utils;
import com.project.moroz.glazes_market.validator.FormErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {
    private UserService userService;
    private ManagerService managerService;
    private SolvencyService solvencyService;
    private FormErrorMessage formErrorMessage;
    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    @Autowired
    public void setSolvencyService(SolvencyService solvencyService) {
        this.solvencyService = solvencyService;
    }

    @Autowired
    public void setFormErrorMessage(FormErrorMessage formErrorMessage) {
        this.formErrorMessage = formErrorMessage;
    }

    @GetMapping
    public String getUsersListPage(Model model, HttpServletRequest request) {
        User user = Utils.getUserInSession(request);
        Manager manager = managerService.returnManagerByLogin(user.getLogin());
        if (request.isUserInRole("ROLE_ADMIN")) {
            List<User> users = userService.returnAllUsers();
            model.addAttribute("users", users);
        } else {
            List<User> users = userService.returnAllUsersByManagerId(manager.getId());
            model.addAttribute("users", users);
        }
        return "users/usersPage";
    }

    @GetMapping("/add")
    public String addPage(Model model, @ModelAttribute("user") User user) {
        List managers = managerService.returnManagersByRoleId(2);
        model.addAttribute("managers", managers);
        List solvencies = solvencyService.returnAllSolvencies();
        model.addAttribute("solvencies", solvencies);
        if (!model.containsAttribute("user")) model.addAttribute("user", user);
        return "users/addUserPage";
    }

    @PostMapping("/add")
    public String addUser(HttpServletRequest request, Model model,
                          @RequestParam(value = "managerId") int managerID,
                          @RequestParam(value = "solvencyId") int solvencyID,
                          @ModelAttribute("user") User user,
                          BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        model.addAttribute("user", user);
        if (userService.isLoginAlreadyInUse(request.getParameter("login"))) {
            String errorMessage = messageSource.getMessage("duplicate.customerForm.name", new Object[]{"duplicate.customerForm.name"}, LocaleContextHolder.getLocale());
            bindingResult.rejectValue("login", "duplicate.customerForm.name", errorMessage);
        }
        double quantity = -1;
        try {
            String number = request.getParameter("discount");
            quantity = Double.parseDouble(number);
        } catch (NumberFormatException e) {
            model.addAttribute("managers", managerService.returnManagersByRoleId(2));
            model.addAttribute("solvencies", solvencyService.returnAllSolvencies());
        }
        String fieldNameError = formErrorMessage.checkFieldName(request.getParameter("name"));
        model.addAttribute("fieldNameError", fieldNameError);
        String fieldLoginError = formErrorMessage.checkFieldLogin(request.getParameter("login"));
        model.addAttribute("fieldLoginError", fieldLoginError);
        String fieldPassError = formErrorMessage.checkFieldPass(request.getParameter("password"));
        model.addAttribute("fieldPassError", fieldPassError);
        String fieldDiscError = formErrorMessage.checkFieldDiscount(request.getParameter("discount"), quantity);
        model.addAttribute("fieldDiscError", fieldDiscError);
        if (!formErrorMessage.checkAddUserFormValid(fieldNameError, fieldLoginError, fieldPassError, fieldDiscError)) {
            model.addAttribute("managers", managerService.returnManagersByRoleId(2));
            model.addAttribute("solvencies", solvencyService.returnAllSolvencies());
            return "users/addUserPage";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("managers", managerService.returnManagersByRoleId(2));
            model.addAttribute("solvencies", solvencyService.returnAllSolvencies());
            redirectAttributes.getFlashAttributes().clear();
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.manager", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            return "users/addUserPage";
        }

        userService.saveUserWithoutPasswordEncode(user);
        userService.setRoleManagerSolvency(user.getId(), managerID, solvencyID);
        return "redirect:/users";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editPage(Model model, @PathVariable("id") int id,
                           @ModelAttribute("user") User user1) {
        User user = userService.returnUserById(id);
        model.addAttribute("user", user);
        List managers = managerService.returnManagersByRoleId(2);
        model.addAttribute("managers", managers);
        List solvencies = solvencyService.returnAllSolvencies();
        model.addAttribute("solvencies", solvencies);
        return "users/editUserPage";
    }

    @PostMapping("/edit")
    public String editProduct(HttpServletRequest request, Model model, @ModelAttribute("user") User user,
                              BindingResult bindingResult,
                              @RequestParam("id") int userId,
                              @RequestParam("name") String name,
                              @RequestParam("login") String login,
                              @RequestParam("password") String password,
                              @RequestParam("discount") double discount,
                              @RequestParam("managerId") int managerId,
                              @RequestParam("solvencyId") int solvencyId, RedirectAttributes redirectAttributes) {
        model.addAttribute("user", user);
        if (userService.isLoginAlreadyInUse(user.getLogin()) && !(user.getLogin().equals(userService.returnUserById(userId).getLogin()))) {
            String errorMessage = messageSource.getMessage("duplicate.customerForm.name", new Object[]{"duplicate.customerForm.name"}, LocaleContextHolder.getLocale());
            bindingResult.rejectValue("login", "duplicate.customerForm.name", errorMessage);
        }
        double quantity = -1;
        try {
            String number = request.getParameter("discount");
            quantity = Double.parseDouble(number);
        } catch (NumberFormatException e) {
            model.addAttribute("managers", managerService.returnManagersByRoleId(2));
            model.addAttribute("solvencies", solvencyService.returnAllSolvencies());
        }
        String fieldNameError = formErrorMessage.checkFieldName(request.getParameter("name"));
        model.addAttribute("fieldNameError", fieldNameError);
        String fieldLoginError = formErrorMessage.checkFieldLogin(request.getParameter("login"));
        model.addAttribute("fieldLoginError", fieldLoginError);
        String fieldPassError = formErrorMessage.checkFieldPass(request.getParameter("password"));
        model.addAttribute("fieldPassError", fieldPassError);
        String fieldDiscError = formErrorMessage.checkFieldDiscount(request.getParameter("discount"), quantity);
        model.addAttribute("fieldDiscError", fieldDiscError);
        if (!formErrorMessage.checkAddUserFormValid(fieldNameError, fieldLoginError, fieldPassError, fieldDiscError)) {
            model.addAttribute("managers", managerService.returnManagersByRoleId(2));
            model.addAttribute("solvencies", solvencyService.returnAllSolvencies());
            return "users/edutUserPage";
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.getFlashAttributes().clear();
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.manager", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            model.addAttribute("managers", managerService.returnManagersByRoleId(2));
            model.addAttribute("solvencies", solvencyService.returnAllSolvencies());
            return "users/editUserPage";
        }

        userService.updateUserWithNewData(userId, name, login, password, discount, managerId, solvencyId);
        return "redirect:/users";
    }
}
