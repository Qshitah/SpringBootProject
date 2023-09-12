package com.login.practice.signUp.controller;

import com.login.practice.signUp.entity.Token;
import com.login.practice.signUp.entity.User;
import com.login.practice.signUp.repository.TokenRepository;
import com.login.practice.signUp.repository.UserRepository;
import com.login.practice.signUp.service.EmailService;
import com.login.practice.signUp.service.TokenService;
import com.login.practice.signUp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;
import java.util.regex.Pattern;

@Controller
public class PasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;






    @InitBinder
    public void initBinder(WebDataBinder dataBinder){

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);

    }

    @GetMapping("/forgot-password")
    public String forgotPassword(){

        return "signup/forgot_password";
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token, @RequestParam("email") String email, Model model){
        // Validate the token and email
        Token verificationToken = tokenRepository.findByToken(token);

        if (verificationToken != null){
            if (!tokenService.isTokenExpired(verificationToken)){
                // Token and email are valid, display the reset password form
                model.addAttribute("token", token);
                model.addAttribute("email", email);

                return "signup/reset_password";
            }else {
                // Token or email is invalid, display an error message
                model.addAttribute("errorMessage", "Invalid token or email");
                return "error";
            }

        }
        return "redirect:/authenticated";
    }

    @PostMapping("/forgot-password")
    public String sendResetPassword(@RequestParam("email") String email, Model theModel){
        // Check if the email exists in your database
        User user = userRepository.findByEmail(email);

        if(user != null){
            // Generate a verification token for the user
            Token token = tokenService.generateToken(user);

            emailService.sendResetPassword(user.getEmail(), token);

            return "redirect:/";
        }

        theModel.addAttribute("errorEmail","Error");
        return "signup/forgot_password";

    }

    @PostMapping("/reset-password")
    public String confirmResetPassword(@RequestParam("token") String token,
                                       @RequestParam("email") String email,
                                       @RequestParam("password") String password,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       Model model,RedirectAttributes redirectAttributes,Authentication authentication){

        model.addAttribute("token", token);
        model.addAttribute("email", email);

        // Validate the new password (you can implement your own validation logic)
        if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",password)) {
            model.addAttribute("errorPassword", "Invalid password at least one letter, at least one digit, a combination of letters and digits, and a minimum length of 8 characters.");
            return "signup/reset_password";
        }

        if(!password.equals(confirmPassword)){
            model.addAttribute("errorEqual", "Passwords doesn't match.");
            return "signup/reset_password";
        }

       if(userService.resetPassword(email, password,token)){

            // Add a success message to be displayed on the dashboard
            redirectAttributes.addFlashAttribute("successPasswordChanged", "Password reset successful!");

            if (authentication != null && authentication.isAuthenticated()){
                return "redirect:/logout";
            }
            return "redirect:/authenticated";
       }
       redirectAttributes.addFlashAttribute("errorPasswordChanged", "Password reset failed!");
       return "redirect:/authenticated";
    }

}
