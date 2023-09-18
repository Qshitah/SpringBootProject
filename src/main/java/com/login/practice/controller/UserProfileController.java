package com.login.practice.controller;

import com.login.practice.Entity.Address;
import com.login.practice.repository.AddressRepository;
import com.login.practice.signUp.entity.User;
import com.login.practice.signUp.repository.UserRepository;
import com.login.practice.signUp.service.EmailService;
import com.login.practice.signUp.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.regex.Pattern;

@Controller
public class UserProfileController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/user-profile")
    public String profile(Model model, Authentication authentication){

        if (model.containsAttribute("updatedSuccess")) {
            // Retrieve the success message and add it to the model
            String successMessage = (String) model.asMap().get("updatedSuccess");
            model.addAttribute("updatedSuccess", successMessage);
        }

        // Get the authenticated user's details
        User user =  userRepository.findByUsername(authentication.getName());

        if(addressRepository.findByUserId(user.getId()) != null){
            Address addressUser = addressRepository.findByUserId(user.getId());
            model.addAttribute("address", addressUser);
        }else {
            model.addAttribute("address",new Address());
        }

        // Pass the user details to the Thymeleaf template
        model.addAttribute("user", user);
        return "user-profile";
    }

    @PostMapping("/user-profile")
    private String updateProfile(@ModelAttribute("user") User user,
                                 @ModelAttribute("address") Address address,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes,
                                 Model model){
        //Current user
        User currentUser = userRepository.findByUsername(authentication.getName());
        Address currentAddress = new Address();
        if(addressRepository.findByUserId(currentUser.getId()) != null){
            currentAddress = addressRepository.findByUserId(currentUser.getId());
        }else {
            currentAddress.setUser(currentUser);
        }

        String firstName = user.getFirstName();
        if(firstName == null || firstName.isEmpty()){
            model.addAttribute("failedFirstName",true);
            return "user-profile";
        }

        String lastName = user.getLastName();
        if(lastName == null || lastName.isEmpty()){
            model.addAttribute("failedLastName",true);
            return "user-profile";
        }

        String email = user.getEmail();
        if((email == null || email.isEmpty()) || !Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",email)){
            model.addAttribute("failedEmail",true);
            return "user-profile";
        }


        String phone = user.getPhone();
        if(!phone.isEmpty() && phone.length() < 5){
            model.addAttribute("failedPhone",true);
            return "user-profile";
        }


        //Update user
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setEmail(email);
        currentUser.setPhone(phone);
        currentUser.setJob_title(user.getJob_title());
        currentUser.setWebsite(user.getWebsite());
        currentUser.setGithub(user.getGithub());
        currentUser.setTwitter(user.getTwitter());
        currentUser.setInstagram(user.getInstagram());
        currentUser.setFacebook(user.getFacebook());
        userRepository.save(currentUser);

        //Save New Address
        currentAddress.setStreetAddress(address.getStreetAddress());
        currentAddress.setCity(address.getCity());
        addressRepository.save(currentAddress);

        //Check if email exists
        if(!currentUser.getEmail().equals(email)){
            redirectAttributes.addFlashAttribute("verifyNewEmail", "Please Verify Your New Email First!");
            return "redirect:/logout" ;
        }


        redirectAttributes.addFlashAttribute("updatedSuccess", "Updated Successfully!");
        return "redirect:/user-profile" ;
    }



}
