package com.login.practice.controller;

import com.dropbox.core.DbxException;
import com.login.practice.Entity.Category;
import com.login.practice.Entity.ParentCategory;
import com.login.practice.Entity.Product;
import com.login.practice.repository.CategoryRepository;
import com.login.practice.repository.ParentCategoryRepository;
import com.login.practice.repository.ProductRepository;
import com.login.practice.service.ImageService;
import com.login.practice.signUp.entity.User;
import com.login.practice.signUp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class AddProductController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ParentCategoryRepository parentCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private final ImageService imageService;

    public AddProductController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/add-product")
    public String addProduct(Model model){
        Product product = new Product();

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        List<ParentCategory> parentCategories = parentCategoryRepository.findAll();
        model.addAttribute("parentCategories", parentCategories);

        model.addAttribute("product", product);



        return "add-product";
    }

    @PostMapping("/add-product")
    public String postAddProduct(@ModelAttribute("product") Product product,
                                 @RequestParam("category") String categoryId,
                                 @RequestParam("parentCategory") String parentCategoryId,
                                 @RequestParam("image-uploadify") List<MultipartFile> files,
                                 Authentication authentication,
                                 Model model)  throws DbxException {


        User currentUser = userRepository.findByUsername(authentication.getName());

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        List<ParentCategory> parentCategories = parentCategoryRepository.findAll();
        model.addAttribute("parentCategories", parentCategories);
        product.setUser(currentUser);


        String productName = product.getProductName();
        if(productName == null || productName.isEmpty()){
            model.addAttribute("failedProductName",true);
            return "add-product";
        }

        if(productRepository.findByProductName(productName) != null){
            model.addAttribute("existsProductName",true);
            return "add-product";
        }

        String sku = product.getSku();
        if(sku == null || sku.isEmpty()){
            model.addAttribute("failedSKU",true);
            return "add-product";
        }

        String description = product.getDescription();
        if(description == null || description.isEmpty()){
            model.addAttribute("failedDescription",true);
            return "add-product";
        }

        if (files.get(0).isEmpty()){
            model.addAttribute("failedFile",true);
            return "add-product";
        }

        Float price = product.getRegularPrice();
        if(price == null || price == 0){
            model.addAttribute("failedPrice",true);
            return "add-product";
        }

        Float salePrice = product.getSalePrice();
        if(salePrice == null ||  salePrice > price){
            model.addAttribute("failedSalePrice",true);
            return "add-product";
        }

        Float costPrice = product.getCostPrice();
        if((costPrice == null || costPrice == 0) || costPrice > price){
            model.addAttribute("failedCostPrice",true);
            return "add-product";
        }

        if(product.getStockQuantity() == null){
            product.setStockQuantity(0);
        }
        if(product.getWeight() == null){
            product.setWeight(0f);
        }

        if(parentCategoryId.isEmpty()){
            model.addAttribute("failedParentCategory",true);
            return "add-product";
        }

        if(categoryId.isEmpty()){
            model.addAttribute("failedCategory",true);
            return "add-product";
        }

        Optional<Category> category = categoryRepository.findById(Integer.valueOf(categoryId));
        category.ifPresent(product::setCategory);

        imageService.uploadImages(files,product);

        product.setIsActive(1);

        productRepository.save(product);

        return "/";

    }

    private String generateUniqueFileName(String originalFileName) {
        // Implement your logic to generate a unique file name
        // This can involve adding timestamps, UUIDs, or other strategies
        // Here's a simple example using a timestamp:
        long timestamp = System.currentTimeMillis();
        return timestamp + "_" + originalFileName;
    }
}
