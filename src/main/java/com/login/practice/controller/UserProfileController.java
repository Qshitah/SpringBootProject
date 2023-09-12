package com.login.practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserProfileController {

    @GetMapping("/user-profile")
    public String profile(){
        return "user-profile";
    }



}
