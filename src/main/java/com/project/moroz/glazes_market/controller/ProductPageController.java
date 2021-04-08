package com.project.moroz.glazes_market.controller;

import com.project.moroz.glazes_market.entity.Product;
import com.project.moroz.glazes_market.entity.User;
import com.project.moroz.glazes_market.service.interfaces.GlazesTypeService;
import com.project.moroz.glazes_market.service.interfaces.OrderService;
import com.project.moroz.glazes_market.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userPage/actionWithProduct")
public class ProductPageController {
    private ProductService productService;
    private GlazesTypeService glazesTypeService;
    private OrderService orderService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setGlazesTypeService(GlazesTypeService glazesTypeService) {
        this.glazesTypeService = glazesTypeService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public String getPage() {
        return "products/actionWithProducts";
    }

    @GetMapping("/addNewProduct")
    public String getAddNewProductPage(Model model, @ModelAttribute("product") Product product) {
        List glazesTypeList = glazesTypeService.returnAllGlazesType();
        model.addAttribute("glazesType", glazesTypeList);
        return "products/addProductPage";
    }

    @GetMapping("/updateProduct")
    public String getProductsToUpdatePage(Model model) {
        List<Product> products = productService.returnAllProducts();
        model.addAttribute("glazeTypes", glazesTypeService.returnAllGlazesType());
        model.addAttribute("products", products);
        Map<Integer, Integer> orderedQuantities = new HashMap<>();
        for (Product product : products) {
            if (productService.returnOrderedProductQuantity(product.getId(), 1) == null) {
                orderedQuantities.put(product.getId(), 0);
            } else {
                orderedQuantities.put(product.getId(), productService.returnOrderedProductQuantity(product.getId(), 1));
            }
        }
        model.addAttribute("orderedQuantity", orderedQuantities);
        boolean hasAnyOrders = false;
        for (Product product : products) {
            if (orderService.returnOrdersIncludeProduct(product.getId()).size() > 0) {
                hasAnyOrders = true;
            }
        }
        model.addAttribute("hasOrders", hasAnyOrders);
        return "products/catalogPage";
    }
}
