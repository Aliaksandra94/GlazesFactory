package com.project.moroz.glazes_market.controller;

import com.project.moroz.glazes_market.entity.BasketItem;
import com.project.moroz.glazes_market.entity.Product;
import com.project.moroz.glazes_market.model.Basket;
import com.project.moroz.glazes_market.service.UserServiceImpl;
import com.project.moroz.glazes_market.service.interfaces.BasketItemService;
import com.project.moroz.glazes_market.service.interfaces.BasketService;
import com.project.moroz.glazes_market.service.interfaces.ProductService;
import com.project.moroz.glazes_market.service.interfaces.UserService;
import com.project.moroz.glazes_market.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/basket")
public class BasketController {
    private BasketService basketService;
    private BasketItemService basketItemService;
    private ProductService productService;
    private UserService userService;

    @Autowired
    public void setBasketService(BasketService basketService) {
        this.basketService = basketService;
    }

    @Autowired
    public void setBasketItemService(BasketItemService basketItemService) {
        this.basketItemService = basketItemService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getBasketPage(HttpServletRequest request, Model model) {
        com.project.moroz.glazes_market.model.Basket basket = Utils.getBasketInSession(request);
        model.addAttribute("basket", basket);
        return "users/basketPage";
    }

    @PostMapping
    public String basketUpdateQty(HttpServletRequest request,
                                  Model model,
                                  @ModelAttribute("basket") Basket basketForm) {
        Basket basketInfo = Utils.getBasketInSession(request);
        basketInfo.updateQuantity(basketForm);
        return "redirect:/basket";
    }

    @GetMapping("/delete")
    public String removeProduct(HttpServletRequest request, Model model, //
                                @RequestParam(value = "idProduct", defaultValue = "") int idProduct) {
        Product product = null;
        if (idProduct != 0) {
            product = productService.returnProductById(idProduct);
        }
        if (product != null) {
            Basket basket = Utils.getBasketInSession(request);
            com.project.moroz.glazes_market.model.Product productInfo = new com.project.moroz.glazes_market.model.Product(product);
            basket.removeProduct(productInfo);
        }
        return "redirect:/basket";
    }
}
