package com.login.practice.controller;

import com.login.practice.Entity.Category;
import com.login.practice.Entity.ParentCategory;
import com.login.practice.Entity.Product;
import com.login.practice.repository.CategoryRepository;
import com.login.practice.repository.ParentCategoryRepository;
import com.login.practice.repository.ProductRepository;
import com.login.practice.service.ImageService;
import com.login.practice.signUp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class EditProductController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ParentCategoryRepository parentCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private final ImageService imageService;

    public EditProductController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/edit-product",params = "productId")
    public String editProduct(@RequestParam("productId") int productId, Model model){

        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()){
            return "error-404";
        }
        model.addAttribute("product", productOptional.get());


        List<String> images = List.of(productOptional.get().getProductImages().split("/"));
        model.addAttribute("images",images);

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        List<ParentCategory> parentCategories = parentCategoryRepository.findAll();
        model.addAttribute("parentCategories", parentCategories);

        return "edit-product";
    }

    @PostMapping(value = "/edit-product",params = "productId")
    public String saveEditProduct(@ModelAttribute("product") Product product,
                                  @RequestParam("category") String categoryId,
                                  @RequestParam("category.parentCategory") String parentCategoryId,
                                  @RequestParam("image-uploadify") List<MultipartFile> files,
                                  @RequestParam("combinedImages") String images,
                                  Model model
                                  ){
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        List<ParentCategory> parentCategories = parentCategoryRepository.findAll();
        model.addAttribute("parentCategories", parentCategories);

        Optional<Product> oldProduct = productRepository.findById(product.getId());

        String productName = product.getProductName();
        if(productName == null || productName.isEmpty()){
            model.addAttribute("failedProductName",true);
            return "add-product";
        }

        if(productRepository.findByProductName(productName) != null && productRepository.findByProductName(productName).getId() != product.getId() ){
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

        if (oldProduct.isPresent()){
            if (!Objects.equals(product.getProductName(), oldProduct.get().getProductName())){
                System.out.println("Ooooh Yeeees");
                // Define the old and new folder paths
                String oldFolderPath = "src/main/resources/static/public/images/products/" + oldProduct.get().getProductName();
                String newFolderPath = "src/main/resources/static/public/images/products/" + product.getProductName();

                // Create File objects for old and new folders
                File oldFolder = new File(oldFolderPath);
                System.out.println(oldFolder);
                File newFolder = new File(newFolderPath);
                System.out.println(newFolder);
                // Check if the old folder exists before renaming
                if (oldFolder.exists()) {
                    if(oldFolder.renameTo(newFolder)){
                        System.out.println("Done Renaming Folder");
                    }
                }
            }
        }

        if (!files.get(0).isEmpty()){
            imageService.uploadImages(files,product);
            product.setProductImages(images + product.getProductImages());
        }else{
            product.setProductImages(images);
        }


        product.setIsActive(1);

        productRepository.save(product);

        List<String> imagesAfterSaving = List.of(product.getProductImages().split("/"));
        model.addAttribute("images",imagesAfterSaving);


        return "redirect:/edit-product?productId=" + product.getId();
    }
}
