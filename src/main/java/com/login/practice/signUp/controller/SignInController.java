package com.login.practice.signUp.controller;


import com.login.practice.signUp.entity.User;
import com.login.practice.signUp.repository.RoleRepository;
import com.login.practice.signUp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class SignInController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;




    //add an initbinder .. to convert trim input strings
    // remove leading and trailing whitespace
    // resolve issue for our validation
    @InitBinder
    public void initBinder(WebDataBinder dataBinder){

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);

    }

    @GetMapping("/authenticated")
    public String showAuthenticatedPage(Model theModel, Authentication authentication, HttpSession session){

        // Check for a success message in the flash attributes
        if (theModel.containsAttribute("successPasswordChanged")) {
            // Retrieve the success message and add it to the model
            String successMessage = (String) theModel.asMap().get("successPasswordChanged");
            theModel.addAttribute("successPasswordChanged", successMessage);
        }
        if (theModel.containsAttribute("errorPasswordChanged")) {
            // Retrieve the success message and add it to the model
            String errorMessage = (String) theModel.asMap().get("errorPasswordChanged");
            theModel.addAttribute("errorPasswordChanged", errorMessage);

        }

        if (authentication != null && authentication.isAuthenticated()){
            return "redirect:/";
        }else {
            if(session.getAttribute("failedSession") != null && !session.getAttribute("failedSession").toString().isEmpty()){
                String username = session.getAttribute("failedSession").toString();
                if (session.getAttribute("notExistsUsername") != null && !session.getAttribute("notExistsUsername").toString().isEmpty()){
                    session.removeAttribute("notExistsUsername");
                    theModel.addAttribute("errorUsername",username);

                }else {
                    if (session.getAttribute("notVerified")!= null && !session.getAttribute("notVerified").toString().isEmpty()){
                        session.removeAttribute("notVerified");
                        theModel.addAttribute("notVerified",true);
                    }
                }

                session.removeAttribute("failedSession");
                theModel.addAttribute("errorFailed",username);

            }
            theModel.addAttribute("user",new User());


            return "signup/login";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // Perform logout logic
        // For example, you can invalidate the session and clear any authentication tokens.
        request.getSession().invalidate();

        // Redirect to a logged-out page or any other desired destination
        return "redirect:/authenticated?logout=true";
    }




}
