package com.project.moroz.glazes_market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartPageController {
    @GetMapping("/")
    public String start() {
        return "start/startPage";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "start/contactPage";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "/403";
    }

    @GetMapping("/info")
    public String info() {
        return "start/companyInfo";
    }
}
