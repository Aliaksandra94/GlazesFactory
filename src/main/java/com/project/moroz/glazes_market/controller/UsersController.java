package com.project.moroz.glazes_market.controller;

import com.project.moroz.glazes_market.entity.*;
import com.project.moroz.glazes_market.service.UserServiceImpl;
import com.project.moroz.glazes_market.service.interfaces.ManagerService;
import com.project.moroz.glazes_market.service.interfaces.RoleService;
import com.project.moroz.glazes_market.service.interfaces.SolvencyService;
import com.project.moroz.glazes_market.service.interfaces.UserService;
import com.project.moroz.glazes_market.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/users")
public class UsersController {
    private UserService userService;
    private ManagerService managerService;
    private SolvencyService solvencyService;
    private RoleService roleService;
    private MessageSource messageSource;

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
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
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
        if(!model.containsAttribute("user"))model.addAttribute("user", user);
        return "users/addUserPage";
    }

    @PostMapping("/add")
    public String addUser(Model model,
                          @RequestParam(value = "managerId") int managerID,
                          @RequestParam(value = "solvencyId") int solvencyID,
                          @ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        model.addAttribute("user", user);
        if (userService.isLoginAlreadyInUse(user.getLogin())){
            bindingResult.rejectValue("login", "error.login", "duplicate.customerForm.name" );
        }
        if (bindingResult.hasErrors()) {
            List managers = managerService.returnManagersByRoleId(2);
            model.addAttribute("managers", managers);
            List solvencies = solvencyService.returnAllSolvencies();
            model.addAttribute("solvencies", solvencies);
            redirectAttributes.getFlashAttributes().clear();
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            return "users/addUserPage";
        }
        userService.saveUserWithoutPasswordEncode(user);
        userService.setRoleManagerSolvency(user.getId(), managerID, solvencyID);
        userService.saveUser(user);
        return "redirect:/users";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        User user = userService.returnUserById(id);
        userService.deleteUser(user);
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
    public String editProduct(Model model, @ModelAttribute("user") @Valid User user,
                              BindingResult bindingResult,
                              @RequestParam("id") int userId,
                              @RequestParam("name") String name,
                              @RequestParam("login") String login,
                              @RequestParam("password") String password,
                              @RequestParam("discount") double discount,
                              @RequestParam("managerId") int managerId,
                              @RequestParam("solvencyId") int solvencyId) {
        model.addAttribute("user", user);
        if (bindingResult.hasErrors()) {
            List managers = managerService.returnManagersByRoleId(2);
            model.addAttribute("managers", managers);
            List solvencies = solvencyService.returnAllSolvencies();
            model.addAttribute("solvencies", solvencies);
            return "users/editUserPage";
        }
        userService.updateUserWithNewData(userId, name, login, password, discount, managerId, solvencyId);
        return "redirect:/users";
    }
}
