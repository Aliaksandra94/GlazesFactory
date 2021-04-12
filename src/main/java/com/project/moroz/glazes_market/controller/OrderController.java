package com.project.moroz.glazes_market.controller;

import com.project.moroz.glazes_market.entity.Order;
import com.project.moroz.glazes_market.entity.User;
import com.project.moroz.glazes_market.model.Basket;
import com.project.moroz.glazes_market.service.UserServiceImpl;
import com.project.moroz.glazes_market.service.interfaces.OrderService;
import com.project.moroz.glazes_market.service.interfaces.UserService;
import com.project.moroz.glazes_market.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/order")
@SessionAttributes("user")
public class OrderController {
    private OrderService orderService;
    private UserService userService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
@Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String getOrderPage(HttpServletRequest request, Model model) {
        Basket basket = Utils.getBasketInSession(request);
        User user = Utils.getUserInSession(request);
        user.setBasket(basket);
        model.addAttribute("basketModel", basket);
        model.addAttribute("basket", user.getBasket());
        model.addAttribute("user", user);
        return "users/orderPage";
    }

    @PostMapping
    public String orderConfirm(HttpServletRequest request, Model model) {
        Basket basket = Utils.getBasketInSession(request);
        User user = Utils.getUserInSession(request);
        try {
            orderService.saveOrder(basket, user.getId());
        } catch (Exception e) {
            return "users/orderPage";
        }
        Utils.removeBasketInSession(request);
        Utils.storeLastOrderedBasketInSession(request, basket);
        Basket lastOrderedCart = Utils.getLastOrderedBasketInSession(request);
        if (lastOrderedCart == null) {
            return "redirect:/basket";
        }
        model.addAttribute("lastOrderedCart", lastOrderedCart);
        return "users/orderReport";
    }
}
