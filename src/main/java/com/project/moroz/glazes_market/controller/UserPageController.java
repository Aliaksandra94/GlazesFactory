package com.project.moroz.glazes_market.controller;

import com.project.moroz.glazes_market.entity.*;
import com.project.moroz.glazes_market.service.interfaces.*;
import com.project.moroz.glazes_market.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/userPage")
public class UserPageController {
    private UserService userService;
    private OrderService orderService;
    private ManagerService managerService;
    private SolvencyService solvencyService;
    private ProductService productService;
    private OrderStageService orderStageService;
    private GlazesTypeService glazesTypeService;
    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setGlazesTypeService(GlazesTypeService glazesTypeService) {
        this.glazesTypeService = glazesTypeService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
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
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setOrderStageService(OrderStageService orderStageService) {
        this.orderStageService = orderStageService;
    }

    @GetMapping()
    public String showUserPage(Model model) {
        return "users/mainPage";
    }

    @GetMapping("/orderHistory")
    public String getOrdersList(HttpServletRequest request, Model model) {
        User user = Utils.getUserInSession(request);
        Manager manager = managerService.returnManagerByLogin(user.getLogin());
        List<OrderStage> orderStages = orderStageService.returnAllOrderStage();
        model.addAttribute("orderStages", orderStages);
        List<Product> products = productService.returnAllProducts();
        model.addAttribute("products", products);
        List<GlazesType> glazesTypes = glazesTypeService.returnAllGlazesType();
        model.addAttribute("glazesTypes", glazesTypes);
        if (request.isUserInRole("ROLE_ADMIN")) {
            List<Order> orders = orderService.returnAllOrders();
            model.addAttribute("orders", orders);
        }
        if (request.isUserInRole("ROLE_SELLER")) {
            List<Order> orders = orderService.returnAllOrdersByManagerId(manager.getId());
            model.addAttribute("orders", orders);
        }
        if (request.isUserInRole("ROLE_PRODUCER")) {
            List<Order> order1 = orderService.returnOrdersByOrderStageId(2);
            List<Order> order2 = orderService.returnOrdersByOrderStageId(3);
            List<Order> order3 = orderService.returnOrdersByOrderStageId(7);
            List<Order> orders = new ArrayList<>();
            for (Order order : order1) {
                orders.add(order);
            }
            for (Order order : order2) {
                orders.add(order);
            }
            for (Order order : order3) {
                orders.add(order);
            }
            model.addAttribute("orders", orders);
        }
        if (request.isUserInRole("ROLE_PURCHASER")) {
            List<Order> orders = orderService.returnOrdersByOrderStageId(6);
            model.addAttribute("orders", orders);
        }
        if (request.isUserInRole("ROLE_USER")) {
            List<Order> orders = orderService.returnAllOrdersByUserId(user.getId());
            model.addAttribute("orders", orders);
        }
        return "users/orderHistoryPage";
    }

    @GetMapping("/orderHistory/activeTasks")
    public String getActiveOrdersList(HttpServletRequest request, Model model) {
        User user = Utils.getUserInSession(request);
        Manager manager = managerService.returnManagerByLogin(user.getLogin());
        List<OrderStage> orderStages = orderStageService.returnAllOrderStage();
        model.addAttribute("orderStages", orderStages);
        if (request.isUserInRole("ROLE_ADMIN")) {
            List<Order> orders = orderService.returnOrdersWithoutOrderStageId(1);
            model.addAttribute("orders", orders);
        }
        if (request.isUserInRole("ROLE_SELLER")) {
            List<Order> orders = orderService.returnOrdersWithoutOrderStageIdByManagerId(1, manager.getId());
            model.addAttribute("orders", orders);
        }
        if (request.isUserInRole("ROLE_USER")) {
            List<Order> orders = orderService.returnOrdersWithoutOrderStageIdByUserId(1, user.getId());
            model.addAttribute("orders", orders);
        }
        return "users/orderHistoryPage";
    }

    @GetMapping("/orderHistory/stage{id}")
    public String getOrdersListUnderStage(HttpServletRequest request, Model model, @PathVariable("id") int orderStageId) {
        User user = Utils.getUserInSession(request);
        Manager manager = managerService.returnManagerByLogin(user.getLogin());
        List<OrderStage> orderStages = orderStageService.returnAllOrderStage();
        model.addAttribute("orderStages", orderStages);
        List<Product> products = productService.returnAllProducts();
        model.addAttribute("products", products);
        if (request.isUserInRole("ROLE_ADMIN")) {
            List<Order> orders = orderService.returnOrdersByOrderStageId(orderStageId);
            model.addAttribute("orders", orders);
        }
        if (request.isUserInRole("ROLE_SELLER")) {
            List<Order> orders = orderService.returnOrdersByOrderStageIdByManagerId(orderStageId, manager.getId());
            model.addAttribute("orders", orders);
        }
        if (request.isUserInRole("ROLE_USER")) {
            List<Order> orders = orderService.returnOrdersByOrderStageIdByUserId(orderStageId, user.getId());
            model.addAttribute("orders", orders);
        }
        return "users/orderHistoryPage";
    }

    @PostMapping("/orderHistory")
    public String getOrdersByUserName(HttpServletRequest request, Model model) {
        User user = Utils.getUserInSession(request);
        Manager manager = managerService.returnManagerByLogin(user.getLogin());
        List<OrderStage> orderStages = orderStageService.returnAllOrderStage();
        model.addAttribute("orderStages", orderStages);
        List<Order> orders = new ArrayList<>();
        List<Product> products = productService.returnAllProducts();
        model.addAttribute("products", products);
        List<GlazesType> glazesTypes = glazesTypeService.returnAllGlazesType();
        model.addAttribute("glazesTypes", glazesTypes);
        if ("findUser".equals(request.getParameter("form"))) {
            String name = request.getParameter("name");
            if (request.isUserInRole("ROLE_ADMIN")) {
                orders = userService.returnUserOrders(name);
            }
            if (request.isUserInRole("ROLE_SELLER")) {
                orders = userService.returnUserOrdersByNameOrLoginAndManagerId(name, manager.getId());
            }
        } else if ("findByOrderId".equals(request.getParameter("form"))) {
            try {
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                if (request.isUserInRole("ROLE_ADMIN")) {
                        Order foundOrder = orderService.returnById(orderId);
                        if(foundOrder !=  null){
                        orders.add(foundOrder);}
                }
                if (request.isUserInRole("ROLE_SELLER")) {
                    Order foundOrder = orderService.returnById(orderId);
                    if(foundOrder != null) {
                        if (foundOrder.getUser().getManager().getId() == manager.getId()) {
                            orders.add(foundOrder);
                        }
                    }
                }
            } catch (NumberFormatException e) {
                String errorType = messageSource.getMessage("error.wrongType", new Object[]{"error.wrongType"}, LocaleContextHolder.getLocale());
                model.addAttribute("error", errorType);
            }
        } else if ("findByProductId".equals(request.getParameter("form"))) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            if (request.isUserInRole("ROLE_ADMIN")) {
                orders = orderService.returnOrdersIncludeProduct(productId);
            } else if (request.isUserInRole("ROLE_SELLER")) {
                List<Order> allOrders = orderService.returnOrdersIncludeProduct(productId);
                for (Order order : allOrders) {
                    if (order.getUser().getManager().getId() == manager.getId()) {
                        orders.add(order);
                    }
                }
            }
        } else if ("findByGlazeType".equals(request.getParameter("form"))) {
            int typeId = Integer.parseInt(request.getParameter("glazeTypeId"));
            if (request.isUserInRole("ROLE_ADMIN")) {
                orders = orderService.returnOrdersIncludeGlazesTypeId(typeId);
            } else if (request.isUserInRole("ROLE_SELLER")) {
                List<Order> allOrders = orderService.returnOrdersIncludeGlazesTypeId(typeId);
                for (Order order : allOrders) {
                    if (order.getUser().getManager().getId() == manager.getId()) {
                        orders.add(order);
                    }
                }
            }
        } else if ("findByPeriod".equals(request.getParameter("form"))) {
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("yyyy-MM-dd");
            Date from = null, to = null;
            try {
                from = format.parse(fromDate);
                to = format.parse(toDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (request.isUserInRole("ROLE_ADMIN")) {
                orders = orderService.returnAllBetweenDates(from, to);
            } else if (request.isUserInRole("ROLE_SELLER")) {
                List<Order> allOrders = orderService.returnAllBetweenDates(from, to);
                for (Order order : allOrders) {
                    if (order.getUser().getManager().getId() == manager.getId()) {
                        orders.add(order);
                    }
                }
            }
        }
        if (orders.size() == 0 || orders == null || orders.isEmpty()) {
            String emptyMessage = messageSource.getMessage("ordersNotFound.message", new Object[]{"ordersNotFound.message"}, LocaleContextHolder.getLocale());
            model.addAttribute("msg", emptyMessage);
        }
        model.addAttribute("orders", orders);
        return "users/orderHistoryPage";
    }

    @GetMapping("/edit/{id}")
    public String getPageForEditPersonalUsersData(Model model, @PathVariable("id") int id, @ModelAttribute("user") User user) {
        User user1 = userService.returnUserById(id);
        model.addAttribute("user", user1);
        return "users/editUserPage";
    }

    @GetMapping("/createGlazeType")
    public String getPageForCreateGlazeType(Model model) {
        return "products/addComposition";
    }

    @GetMapping("/updateGlazeType/{id}")
    public String getPageForUpdateGlazeType(Model model, @PathVariable("id") int id) {
        return "products/addComposition";
    }

    @GetMapping("/updateGlazeType")
    public String getPageForUpdateGlazeType(Model model) {
        return "products/addComposition";
    }

    @GetMapping("/createProduct")
    public String getPageForCreateProduct(Model model) {
        return "redirect:/catalog/add";
    }

    @GetMapping("/updateProduct")
    public String getPageForUpdateProduct(Model model) {
        return "redirect:/catalog/add";
    }

    @GetMapping("/editManager/{id}")
    public String getPageForEditPersonalManagerData(Model model, @PathVariable("id") int id) {
        User user1 = userService.returnUserById(id);
        if (managerService.returnManagerByLogin(user1.getLogin()) != null) {
            model.addAttribute("manager", managerService.returnManagerByLogin(user1.getLogin()));
        }
        return "managers/editManagerPage";
    }

    @PostMapping("/edit")
    public String editPersonalUsersData(HttpServletRequest request, Model
            model, @ModelAttribute("user") @Valid User user,
                                        BindingResult bindingResult,
                                        @ModelAttribute("manager") @Valid Manager manager,
                                        @RequestParam("id") int userId,
                                        @RequestParam("name") String name,
                                        @RequestParam("login") String login,
                                        @RequestParam("password") String password,
                                        @RequestParam("email") String email,
                                        @RequestParam("discount") double discount,
                                        @RequestParam("manager.name") String managerId,
                                        @RequestParam("solvency.name") String solvencyId) {
        model.addAttribute("user", user);
        if (bindingResult.hasErrors()) {
            return "users/editUserPage";
        }
        Manager manager1 = managerService.returnManagerByName(managerId);
        Solvency solvency = solvencyService.returnSolvencyByName(solvencyId);
        userService.updateUserWithNewDataAndEmail(userId, name, login, password, discount, manager1.getId(), solvency.getId(), email);
        return "redirect:/userPage";
    }

    @PostMapping("/editManager")
    public String editPersonalManagersData(HttpServletRequest request, Model
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


    @GetMapping("/orderHistory/orderDetails/{id}")
    public String getOrderDetails(HttpServletRequest request, Model model,
                                  @PathVariable("id") int id) {
        Order order = orderService.returnOrderById(id);
        model.addAttribute("order", order);
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            List<OrderStage> orderStages = orderStageService.returnAllOrderStage();
            model.addAttribute("orderStages", orderStages);
        }
        return "users/orderDetails";
    }

    @PostMapping("/orderHistory/orderDetails")
    public String getNewOrderDetails(HttpServletRequest request, Model model, @ModelAttribute("order") Order
            order,
                                     @RequestParam("orderId") int orderId,
                                     @RequestParam("orderStagedId") int orderStageId) {
        Order orderForUpdate = orderService.returnOrderById(orderId);
        orderForUpdate.setOrderStage(orderStageService.returnOrderStageById(orderStageId));
        productService.updateProductQuantity(orderId);
        return getOrdersList(request, model);
    }


}
