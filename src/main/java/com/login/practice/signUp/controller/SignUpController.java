package com.login.practice.signUp.controller;

import com.login.practice.signUp.entity.Role;
import com.login.practice.signUp.entity.User;
import com.login.practice.signUp.repository.RoleRepository;
import com.login.practice.signUp.repository.UserRepository;
import com.login.practice.signUp.service.RegistrationService;
import com.login.practice.signUp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


@Controller
public class SignUpController {

    private UserRepository userRepository;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserService userService;

    public SignUpController(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    //add an initbinder .. to convert trim input strings
    // remove leading and trailing whitespace
    // resolve issue for our validation
    @InitBinder
    public void initBinder(WebDataBinder dataBinder){

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);

    }

    @GetMapping("/signup")
    public String showSignUp(Model theModel, Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()){
            return "redirect:/";
        }else {
            //create model attribute to bind form data

            theModel.addAttribute("user",new User());
            return "signup/signUp";
        }

    }
    // add request mapping for /access-denied
    @PostMapping("/signup")
    public String signup(
            @Valid @ModelAttribute("user") User user,
            BindingResult theBindingResult,
            Model model
            ){

        if(theBindingResult.hasErrors()){
            return "signup/signUp";
        }else{
            if(userRepository.findByUsername(user.getUsername()) != null){
                model.addAttribute("errorUsername","Error");
                return "signup/signUp";
            }
            if (userRepository.findByEmail(user.getEmail()) != null){
                model.addAttribute("errorEmail","Error");
                return "signup/signUp";
            }
            registrationService.registerUser(user);
            return "redirect:/authenticated";
        }

    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("token") String token) {
        boolean verified = userService.verifyUser(token);
        if (verified) {
            return ResponseEntity.ok("User verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
    }
}
