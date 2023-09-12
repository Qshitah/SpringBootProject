package com.login.practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/user-profile")
    public String homePage(){
        return "user-profile";
    }



}
