package com.project.moroz.glazes_market.controller;

import com.project.moroz.glazes_market.entity.*;
import com.project.moroz.glazes_market.service.interfaces.*;
import com.project.moroz.glazes_market.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/userPage/reports")
public class ReportsController {
    private UserService userService;
    private OrderStageService orderStageService;
    private OrderService orderService;
    private ManagerService managerService;
    private MessageSource messageSource;
    private ProductService productService;
    private GlazesTypeService glazesTypeService;
    private RoleService roleService;
    private RawMaterialService rawMaterialService;
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setRawMaterialService(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setGlazesTypeService(GlazesTypeService glazesTypeService) {
        this.glazesTypeService = glazesTypeService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setOrderStageService(OrderStageService orderStageService) {
        this.orderStageService = orderStageService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showAllReportsPage(Model model) {
        return "reports/listOfReports";
    }

    @GetMapping("/onClients")
    public String showClientsReportsPage(HttpServletRequest request, Model model) {
        User user = Utils.getUserInSession(request);
        Manager manager = managerService.returnManagerByLogin(user.getLogin());
        List<User> users = new ArrayList<>();
        if (request.isUserInRole("ADMIN")) {
            users = userService.returnAllUsers();
        } else if (request.isUserInRole("SELLER")) {
            users = userService.returnAllUsersByManagerId(manager.getId());
        }
        model.addAttribute("users", users);
        List<OrderStage> orderStages = orderStageService.returnAllOrderStage();
        model.addAttribute("stages", orderStages);
        List<Integer> orderStageId = orderStageService.returnAllOrderStageId();
        Map<User, List<Double>> allOrdersSum = new HashMap<>();
        for (User client : users) {
            List<Double> sums = new ArrayList<>();
            if (orderService.returnSumOfOrders(client.getId()) == null) {
                sums.add(0.00);
            } else {
                sums.add(orderService.returnSumOfOrders(client.getId()));
            }
            for (Integer id : orderStageId) {
                if (orderService.returnSumOfOrdersByUserAndStage(client.getId(), id) == null) {
                    sums.add(0.00);
                } else {
                    sums.add(orderService.returnSumOfOrdersByUserAndStage(client.getId(), id));
                }
            }
            allOrdersSum.put(client, sums);
        }
        model.addAttribute("allOrdersSum", allOrdersSum);
        return "reports/clientsReport";
    }

    @PostMapping("/onClients")
    public String findClientReportsPage(HttpServletRequest request, Model model, @RequestParam("name") String name) {
        User user = Utils.getUserInSession(request);
        Manager manager = managerService.returnManagerByLogin(user.getLogin());
        List<OrderStage> orderStages = orderStageService.returnAllOrderStage();
        model.addAttribute("stages", orderStages);
        List<Integer> orderStageId = orderStageService.returnAllOrderStageId();
        Map<User, List<Double>> allOrdersSum = new HashMap<>();
        List<User> users = new ArrayList<>();
        if (request.isUserInRole("ADMIN")) {
            users = userService.returnUserOrUsersByLoginOrName(name);
        } else if (request.isUserInRole("SELLER")) {
            users = userService.returnUserOrUsersByLoginOrNameAndManagerId(name, manager.getId());
        }
        for (User client : users) {
            List<Double> sums = new ArrayList<>();
            if (orderService.returnSumOfOrders(client.getId()) == null) {
                sums.add(0.00);
            } else {
                sums.add(orderService.returnSumOfOrders(client.getId()));
            }
            for (Integer id : orderStageId) {
                if (orderService.returnSumOfOrdersByUserAndStage(client.getId(), id) == null) {
                    sums.add(0.00);
                } else {
                    sums.add(orderService.returnSumOfOrdersByUserAndStage(client.getId(), id));
                }
            }
            allOrdersSum.put(client, sums);
        }
        if (users.size() == 0 || users == null || users.isEmpty()) {
            String emptyMessage = messageSource.getMessage("ordersNotFound.message", new Object[]{"ordersNotFound.message"}, LocaleContextHolder.getLocale());
            model.addAttribute("msg", emptyMessage);
        }
        model.addAttribute("allOrdersSum", allOrdersSum);
        return "reports/clientsReport";
    }


    @GetMapping("/onProducts")
    public String showProductsReportsPage(HttpServletRequest request, Model model) {
        List<Product> products = productService.returnAllProducts();
        model.addAttribute("products", products);
        Map<Product, List<Double>> productListMap = productService.returnProductWithQuantities(products);
        model.addAttribute("productsWithAmountAndQuantities", productListMap);
        model.addAttribute("glazeTypes", glazesTypeService.returnAllGlazesType());
        return "reports/productsReports";
    }

    @PostMapping("/onProducts")
    public String getProductsReportsPage(HttpServletRequest request, Model model) {
        List<Product> products = productService.returnAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("glazeTypes", glazesTypeService.returnAllGlazesType());
        Map<Product, List<Double>> productListMap = new HashMap<>();
        if ("findProduct".equals(request.getParameter("form"))) {
            try {
                int productId = Integer.parseInt(request.getParameter("productId"));
                List<Product> foundedProduct = new ArrayList<>();
                foundedProduct.add(productService.returnProductById(productId));
                productListMap = productService.returnProductWithQuantities(foundedProduct);

            } catch (NumberFormatException e) {
                String errorType = messageSource.getMessage("error.wrongType", new Object[]{"error.wrongType"}, LocaleContextHolder.getLocale());
                model.addAttribute("error", errorType);
            }
        } else if ("findByType".equals(request.getParameter("form"))) {
            int typeId = Integer.parseInt(request.getParameter("glazeTypeId"));
            List<Product> productList = productService.returnAllByGlazesTypeId(typeId);
            productListMap = productService.returnProductWithQuantities(productList);
        }
//        else if("sortReadyQuantity".equals(request.getParameter("form"))) {
//            int sortBySum = Integer.parseInt(request.getParameter("sortById"));
//            if (sortBySum == 1) {
//                List<Product> productList = productService.sortByQuantityReadyASC(1);
//                productListMap = productService.returnProductWithQuantities(productList);
//            }
//            if (sortBySum == 2) {
//                List<Product> productList = productService.sortByQuantityReadyDESC(1);
//                productListMap = productService.returnProductWithQuantities(productList);
//            }
//        } else if("sortOrderedQuantity".equals(request.getParameter("form"))) {
//            int sortBySum = Integer.parseInt(request.getParameter("sortById"));
//            if (sortBySum == 1) {
//                List<Product> productList = productService.sortByQuantityOrderedASC(1);
//                productListMap = productService.returnProductWithQuantities(productList);
//            }
//            if (sortBySum == 2) {
//                List<Product> productList = productService.sortByQuantityOrderedDESC(1);
//                productListMap = productService.returnProductWithQuantities(productList);
//            }
//        }
//        else if("sortReadyAmount".equals(request.getParameter("form"))) {
//            int sortBySum = Integer.parseInt(request.getParameter("sortById"));
//            if (sortBySum == 1) {
//                List<Product> productList = productService.sortByAmountReadyASC(1);
//                productListMap = productService.returnProductWithQuantities(productList);
//            }
//            if (sortBySum == 2) {
//                List<Product> productList = productService.sortByAmountReadyDESC(1);
//                productListMap = productService.returnProductWithQuantities(productList);
//            }
//        }
//        else if("sortOrderedAmount".equals(request.getParameter("form"))) {
//            int sortBySum = Integer.parseInt(request.getParameter("sortById"));
//            if (sortBySum == 1) {
//                List<Product> productList = productService.sortByAmountOrderedASC(1);
//                productListMap = productService.returnProductWithQuantities(productList);
//            }
//            if (sortBySum == 2) {
//                List<Product> productList = productService.sortByAmountOrderedDESC(1);
//                productListMap = productService.returnProductWithQuantities(productList);
//            }
//        }
        model.addAttribute("productsWithAmountAndQuantities", productListMap);
        return "reports/productsReports";
    }

    @GetMapping("/onManagers")
    public String showManagersReportsPage(Model model) {
        model.addAttribute("managers", managerService.returnAllManagersWithoutAdmin());
        model.addAttribute("roles", roleService.returnAllRolesWithoutSeveralRoles(1, 4));
        model.addAttribute("managersOrders", managerService.returnManagersOrdersSumAndCount(managerService.returnAllManagersWithoutAdmin()));
        return "reports/managersReports";
    }

    @PostMapping("/onManagers")
    public String getManagersReportsPage(HttpServletRequest request, Model model) {
        model.addAttribute("managers", managerService.returnAllManagersWithoutAdmin());
        model.addAttribute("roles", roleService.returnAllRolesWithoutSeveralRoles(1, 4));
        List<Manager> managers = new ArrayList<>();
        if ("findByNameOrLogin".equals(request.getParameter("form"))) {
            String name = request.getParameter("name");
            managers = managerService.returnManagersByUsers(userService.returnUserOrUsersByLoginOrName(name));
        }
        if ("findByRole".equals(request.getParameter("form"))) {
            int roleId = Integer.parseInt(request.getParameter("roleId"));
            managers = managerService.returnManagersByRoleId(roleId);
        }
        if (managers.size() == 0 || managers == null || managers.isEmpty()) {
            String emptyMessage = messageSource.getMessage("managersNotFound.message", new Object[]{"managersNotFound.message"}, LocaleContextHolder.getLocale());
            model.addAttribute("msg", emptyMessage);
        }
        model.addAttribute("managersOrders", managerService.returnManagersOrdersSumAndCount(managers));
        return "reports/managersReports";
    }

    @GetMapping("/onOrders")
    public String showOrdersReportsPage(HttpServletRequest request, Model model) {
        User user = Utils.getUserInSession(request);
        Manager manager = managerService.returnManagerByLogin(user.getLogin());
        List<OrderStage> orderStages = orderStageService.returnAllOrderStage();
        model.addAttribute("orderStages", orderStages);
        List<Order> orders = new ArrayList<>();
        if (request.isUserInRole("ROLE_ADMIN")) {
            orders = orderService.returnAllOrders();
        }
        if (request.isUserInRole("ROLE_SELLER")) {
            orders = orderService.returnAllOrdersByManagerId(manager.getId());
        }
        if (request.isUserInRole("ROLE_PRODUCER")) {
            List<Order> order1 = orderService.returnOrdersByOrderStageId(2);
            List<Order> order2 = orderService.returnOrdersByOrderStageId(3);
            List<Order> order3 = orderService.returnOrdersByOrderStageId(7);
            for (Order order : order1) {
                orders.add(order);
            }
            for (Order order : order2) {
                orders.add(order);
            }
            for (Order order : order3) {
                orders.add(order);
            }
        }
        model.addAttribute("orders", orders);
        model.addAttribute("ordersAmounts", userService.getOrdersAmount(orders));
        return "reports/ordersReports";
    }

    @PostMapping("/onOrders")
    public String getReportOrdersReportsPage(HttpServletRequest request, Model model) {
        User user = Utils.getUserInSession(request);
        Manager manager = managerService.returnManagerByLogin(user.getLogin());
        List<OrderStage> orderStages = orderStageService.returnAllOrderStage();
        model.addAttribute("orderStages", orderStages);
        List<Order> orders = new ArrayList<>();
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
                    if (foundOrder != null) {
                        orders.add(foundOrder);
                    }
                }
                if (request.isUserInRole("ROLE_PRODUCER")) {
                    Order foundOrder = orderService.returnById(orderId);
                    if (foundOrder != null) {
                        if (foundOrder.getOrderStage().getOrderStageID() == 2 || foundOrder.getOrderStage().getOrderStageID() == 3
                                || foundOrder.getOrderStage().getOrderStageID() == 7) {
                            orders.add(foundOrder);
                        }
                    }
                }
                if (request.isUserInRole("ROLE_SELLER")) {
                    Order foundOrder = orderService.returnById(orderId);
                    if (foundOrder != null) {
                        if (foundOrder.getOrderStage().getOrderStageID() == manager.getId()) {
                            orders.add(foundOrder);
                        }
                    }
                }
            } catch (NumberFormatException e) {
                String errorType = messageSource.getMessage("error.wrongType", new Object[]{"error.wrongType"}, LocaleContextHolder.getLocale());
                model.addAttribute("error", errorType);
            }

        } else if ("findByOrderStage".equals(request.getParameter("form"))) {
            int orderStageId = Integer.parseInt(request.getParameter("orderStageId"));
            if (request.isUserInRole("ROLE_ADMIN")) {
                orders = orderService.returnOrdersByOrderStageId(orderStageId);
            }
            if (request.isUserInRole("ROLE_SELLER")) {
                orders = orderService.returnOrdersByOrderStageIdByManagerId(orderStageId, manager.getId());
            }
            if (request.isUserInRole("ROLE_PRODUCER")) {
                if (orderStageId == 2 || orderStageId == 3 || orderStageId == 7) {
                    orders = orderService.returnOrdersByOrderStageId(orderStageId);
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
            } else if (request.isUserInRole("ROLE_PRODUCER")) {
                List<Order> allOrders = orderService.returnAllBetweenDates(from, to);
                for (Order order : allOrders) {
                    if (order.getOrderStage().getOrderStageID() == 2 || order.getOrderStage().getOrderStageID() == 3 || order.getOrderStage().getOrderStageID() == 7) {
                        orders.add(order);
                    }
                }
            }
        } else if ("sortByDate".equals(request.getParameter("form"))) {
            int sortById = Integer.parseInt(request.getParameter("sortById"));
            if (sortById == 1) {
                if (request.isUserInRole("ROLE_ADMIN")) {
                    orders = orderService.returnAllOrdersWithSortedDateASC();
                }
                if (request.isUserInRole("ROLE_SELLER")) {
                    orders = orderService.returnAllOrdersByManagerIdWithSortedDateASC(manager.getId());
                }
                if (request.isUserInRole("ROLE_PRODUCER")) {
                    List<Order> order1 = orderService.returnAllOrdersByOrderStageWithSortedDateASC(2);
                    List<Order> order2 = orderService.returnAllOrdersByOrderStageWithSortedDateASC(3);
                    List<Order> order3 = orderService.returnAllOrdersByOrderStageWithSortedDateASC(7);
                    for (Order order : order1) {
                        orders.add(order);
                    }
                    for (Order order : order2) {
                        orders.add(order);
                    }
                    for (Order order : order3) {
                        orders.add(order);
                    }
                }
            }
            if (sortById == 2) {
                if (request.isUserInRole("ROLE_ADMIN")) {
                    orders = orderService.returnAllOrdersWithSortedDateDesc();
                }
                if (request.isUserInRole("ROLE_SELLER")) {
                    orders = orderService.returnAllOrdersByManagerIdWithSortedDateDESC(manager.getId());
                }
                if (request.isUserInRole("ROLE_PRODUCER")) {
                    List<Order> order1 = orderService.returnAllOrdersByOrderStageWithSortedDateDesc(2);
                    List<Order> order2 = orderService.returnAllOrdersByOrderStageWithSortedDateDesc(3);
                    List<Order> order3 = orderService.returnAllOrdersByOrderStageWithSortedDateDesc(7);
                    for (Order order : order1) {
                        orders.add(order);
                    }
                    for (Order order : order2) {
                        orders.add(order);
                    }
                    for (Order order : order3) {
                        orders.add(order);
                    }
                }
            }
        } else if ("sortBySum".equals(request.getParameter("form"))) {
            int sortBySum = Integer.parseInt(request.getParameter("sortById"));
            if (sortBySum == 1) {
                if (request.isUserInRole("ROLE_ADMIN")) {
                    orders = orderService.returnAllOrdersWithSortedSumASC();
                }
                if (request.isUserInRole("ROLE_SELLER")) {
                    orders = orderService.returnAllOrdersByManagerIdWithSortedSumASC(manager.getId());
                }
                if (request.isUserInRole("ROLE_PRODUCER")) {
                    List<Order> order1 = orderService.returnAllOrdersByOrderStageWithSortedSumASC(2);
                    List<Order> order2 = orderService.returnAllOrdersByOrderStageWithSortedSumASC(3);
                    List<Order> order3 = orderService.returnAllOrdersByOrderStageWithSortedSumASC(7);
                    for (Order order : order1) {
                        orders.add(order);
                    }
                    for (Order order : order2) {
                        orders.add(order);
                    }
                    for (Order order : order3) {
                        orders.add(order);
                    }
                }
            }
            if (sortBySum == 2) {
                if (request.isUserInRole("ROLE_ADMIN")) {
                    orders = orderService.returnAllOrdersWithSortedSumDesc();
                }
                if (request.isUserInRole("ROLE_SELLER")) {
                    orders = orderService.returnAllOrdersByManagerIdWithSortedSumDESC(manager.getId());
                }
                if (request.isUserInRole("ROLE_PRODUCER")) {
                    List<Order> order1 = orderService.returnAllOrdersByOrderStageWithSortedSumDesc(2);
                    List<Order> order2 = orderService.returnAllOrdersByOrderStageWithSortedSumDesc(3);
                    List<Order> order3 = orderService.returnAllOrdersByOrderStageWithSortedSumDesc(7);
                    for (Order order : order1) {
                        orders.add(order);
                    }
                    for (Order order : order2) {
                        orders.add(order);
                    }
                    for (Order order : order3) {
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
        model.addAttribute("ordersAmounts", userService.getOrdersAmount(orders));
        return "reports/ordersReports";
    }

    @GetMapping("/onRaw")
    public String showRawReportsPage(Model model) {
        model.addAttribute("allRaws", rawMaterialService.returnAllRawMaterials());
        model.addAttribute("categories", categoryService.returnAllCategories());
        model.addAttribute("glazesType", glazesTypeService.returnAllGlazesType());
        model.addAttribute("allProducts", productService.returnAllProducts());
        model.addAttribute("raws", rawMaterialService.returnRawMaterialsAndQuantitiesForProductInProcess(productService.returnAllProducts()));
        return "reports/rawReports";
    }

    @PostMapping("/onRaw")
    public String getRawReportsPage(HttpServletRequest request, Model model) {
        model.addAttribute("allRaws", rawMaterialService.returnAllRawMaterials());
        model.addAttribute("allProducts", productService.returnAllProducts());
        model.addAttribute("categories", categoryService.returnAllCategories());
        model.addAttribute("glazesType", glazesTypeService.returnAllGlazesType());
        List<Product> products = new ArrayList<>();
        if ("findByProduct".equals(request.getParameter("form"))) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            products.add(productService.returnProductById(productId));
            }
        model.addAttribute("raws", rawMaterialService.returnRawMaterialsAndQuantitiesForProductInProcess(products));
        return "reports/rawReports";
    }
}
