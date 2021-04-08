package com.project.moroz.glazes_market.controller;

import com.project.moroz.glazes_market.entity.Order;
import com.project.moroz.glazes_market.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminController {
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/users/{id}/orders")
    public String getUserOrders(Model model, @PathVariable("id") int userId) {
        List<Order> orders = orderService.returnAllOrdersByUserId(userId);
        model.addAttribute("orders", orders);
        return "users/orderHistoryPage";
    }
}
