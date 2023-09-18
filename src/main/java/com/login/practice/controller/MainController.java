package com.login.practice.controller;

import com.login.practice.signUp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String homePage(Authentication authentication){
        if (userRepository.findByUsername(authentication.getName()) == null){
            return "redirect:/logout";
        }
        return "index";
    }

}
