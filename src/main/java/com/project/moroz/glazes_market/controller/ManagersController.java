package com.project.moroz.glazes_market.controller;

import com.project.moroz.glazes_market.entity.Manager;
import com.project.moroz.glazes_market.entity.User;
import com.project.moroz.glazes_market.service.interfaces.ManagerService;
import com.project.moroz.glazes_market.service.interfaces.OrderService;
import com.project.moroz.glazes_market.service.interfaces.RoleService;
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

@Controller
@RequestMapping("/managers")
public class ManagersController {
    private ManagerService managerService;
    private RoleService roleService;
    private OrderService orderService;
    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String getManagersListPage(Model model, HttpServletRequest request) {
        List<Manager> managers = managerService.returnAllManagers();
        model.addAttribute("managers", managers);
        return "managers/managersListPage";
    }

    @GetMapping("/add")
    public String addPage(Model model, @ModelAttribute("manager") Manager manager) {
        List roles = roleService.returnAllRoles();
        model.addAttribute("roles", roles);
        if (!model.containsAttribute("manager")) model.addAttribute("manager", manager);
        return "managers/addManagerPage";
    }

    @PostMapping("/add")
    public String addManager(Model model,
                             @RequestParam(value = "roleId") int roleID,
                             @ModelAttribute("manager") @Valid Manager manager,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        model.addAttribute("manager", manager);
        if (managerService.isLoginAlreadyInUse(manager.getLogin())) {
            String errorMessage = messageSource.getMessage("duplicate.customerForm.name", new Object[]{"duplicate.customerForm.name"}, LocaleContextHolder.getLocale());
            bindingResult.rejectValue("login", "duplicate.customerForm.name", errorMessage);
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("manager", manager);
            List roles = roleService.returnAllRoles();
            model.addAttribute("roles", roles);
            redirectAttributes.getFlashAttributes().clear();
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.manager", bindingResult);
            redirectAttributes.addFlashAttribute("manager", manager);
            return "managers/addManagerPage";
        }
        managerService.saveManagerWithoutPasswordEncode(manager);
        managerService.setRole(manager.getId(), roleID);
        managerService.saveManager(manager);
        return "redirect:/managers";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        Manager manager = managerService.returnManagerById(id);
        managerService.deleteManager(manager);
        return "redirect:/managers";
    }

    @GetMapping("/edit/{id}")
    public String editPage(Model model, @PathVariable("id") int id) {
        Manager manager = managerService.returnManagerById(id);
        model.addAttribute("manager", manager);
        List managers = managerService.returnAllManagers();
        model.addAttribute("managers", managers);
        return "managers/editManagerPage";
    }

    @PostMapping("/edit")
    public String editManagerData(HttpServletRequest request, Model
            model, @ModelAttribute("manager") @Valid Manager manager,
                                  BindingResult bindingResult,
                                  @RequestParam("id") int managerId,
                                  @RequestParam("name") String name,
                                  @RequestParam("login") String login,
                                  @RequestParam("password") String password) {
        if (bindingResult.hasErrors()) {
            return "managers/editManagerPage";
        }
        Manager manager1 = managerService.returnManagerById(managerId);
        managerService.updateManagerWithNewData(manager1.getId(), name, login, password, manager1.getUsers());
        return "redirect:/userPage";
    }
}
