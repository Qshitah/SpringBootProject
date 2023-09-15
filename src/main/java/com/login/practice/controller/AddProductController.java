package com.login.practice.controller;

import com.login.practice.Entity.Category;
import com.login.practice.Entity.ParentCategory;
import com.login.practice.Entity.Product;
import com.login.practice.repository.ProductRepository;
import com.login.practice.signUp.entity.User;
import com.login.practice.signUp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AddProductController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/add-product")
    public String addProduct(Model model, Authentication authentication){
        Product product = new Product();
        Category category = new Category();
        ParentCategory parentCategory = new ParentCategory();

        model.addAttribute("product", product);
        model.addAttribute("category", category);
        model.addAttribute("parentCategory", parentCategory);


        return "add-product";
    }

    /*@PostMapping("/add-product")
    public String postAddProduct(@ModelAttribute("product") Product product,
                                 @ModelAttribute("category") Category category,
                                 @ModelAttribute("parentCategory") ParentCategory parentCategory,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes,
                                 Model model){
        User currentUser = userRepository.findByUsername(authentication.getName());


    }*/
}
