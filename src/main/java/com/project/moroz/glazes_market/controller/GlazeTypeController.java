package com.project.moroz.glazes_market.controller;

import com.project.moroz.glazes_market.entity.GlazesType;
import com.project.moroz.glazes_market.service.interfaces.CategoryService;
import com.project.moroz.glazes_market.service.interfaces.GlazesTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userPage/actionWithGlazeType")
public class GlazeTypeController {
    private GlazesTypeService glazesTypeService;
    private CategoryService categoryService;

    @Autowired
    public void setGlazesTypeService(GlazesTypeService glazesTypeService) {
        this.glazesTypeService = glazesTypeService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public String getPage() {
        return "products/actionWithGlazeType";
    }

    @GetMapping("/addNewGlazeType")
    public String getAddNewGlazeTypePage(Model model, @ModelAttribute("glazeType") GlazesType glazesType) {
        model.addAttribute("categories", categoryService.returnAllCategories());
        return "products/addComposition";
    }

    @PostMapping("/addNewGlazeType")
    public String addNewGlazeType(@ModelAttribute("glazeType") GlazesType glazesType) {

        return "products/addComposition";
    }

    @GetMapping("/updateGlazeType")
    public String getGlazeTypeToUpdatePage() {
        return "products/changeComposition";
    }

    @GetMapping("/updateGlazeType/{id}")
    public String getUpdateProductPage() {
        return "products/changeComposition";
    }
}
