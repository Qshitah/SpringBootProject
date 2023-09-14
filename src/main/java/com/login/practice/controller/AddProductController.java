package com.login.practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddProductController {

    @GetMapping("/add-product")
    public String AddProduct(){
        return "add-product";
    }
}
