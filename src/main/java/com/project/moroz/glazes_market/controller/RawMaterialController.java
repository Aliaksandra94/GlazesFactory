package com.project.moroz.glazes_market.controller;

import com.project.moroz.glazes_market.entity.OrderItem;
import com.project.moroz.glazes_market.entity.Product;
import com.project.moroz.glazes_market.entity.RawMaterial;
import com.project.moroz.glazes_market.service.interfaces.OrderService;
import com.project.moroz.glazes_market.service.interfaces.RawMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userPage/actionWithRawMaterial")
public class RawMaterialController {
    private RawMaterialService rawMaterialService;
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setRawMaterialService(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @GetMapping()
    public String getPage() {
        return "products/actionWithRawMaterial";
    }

    @GetMapping("/addNewRawMaterial")
    public String getAddNewProductPage() {
        return "products/addRawMaterial";
    }

    @GetMapping("/updateRawMaterial")
    public String getProductsToUpdatePage() {
        return "products/changeRawMaterial";
    }

    @GetMapping("/changeRawMaterialQuantity/{id}/{orderId}")
    public String editRawMaterialQuantityPage(Model model, @PathVariable("id") int id, @PathVariable("orderId") int orderId) {
        RawMaterial rawMaterial = rawMaterialService.returnRawMaterialById(id);
        model.addAttribute("raw", rawMaterial);
        model.addAttribute("orderId", orderId);
        model.addAttribute("quantity", rawMaterialService.returnRawMaterialQuantityAreNotEnough(rawMaterial.getRawMaterialID(), orderService.returnOrderById(orderId)));
        return "products/changeRawMaterial";
    }

    @PostMapping("/changeRawMaterialQuantity")
    public String editProductQuantity(@RequestParam("id") int productId,
                                      @RequestParam("quantity") int quantity,
                                      @RequestParam("orderId") int orderId) {
        rawMaterialService.updateRawMaterialQuantity(productId, quantity);
        return "redirect:/userPage/orderHistory";
    }
}