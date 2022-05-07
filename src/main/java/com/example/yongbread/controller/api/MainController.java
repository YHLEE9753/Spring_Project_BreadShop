package com.example.yongbread.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/api")
    public String mainOrder(Model model){
        return "/api/main";
    }
}
