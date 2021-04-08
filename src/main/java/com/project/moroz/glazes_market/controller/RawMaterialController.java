package com.project.moroz.glazes_market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userPage/actionWithRawMaterial")
public class RawMaterialController {
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
}