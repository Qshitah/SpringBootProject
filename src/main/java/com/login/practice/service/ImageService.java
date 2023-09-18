package com.login.practice.service;

import com.login.practice.Entity.ParentCategory;
import com.login.practice.Entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {


    public List<String> uploadImages(List<MultipartFile> files,Product product) {
        List<String> responses = new ArrayList<>();
        StringBuilder images = new StringBuilder();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String fileName = file.getOriginalFilename();
                    String filePath = "src/main/resources/static/public/images/products/"+ product.getProductName() + "/"  + fileName;

                    // Create the file on the server
                    File imageFile = new File(filePath);

                    // Ensure the directory exists
                    if (!imageFile.getParentFile().exists()) {
                        imageFile.getParentFile().mkdirs();
                    }

                    // Write the bytes of the uploaded file to the server
                    try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                        fos.write(file.getBytes());
                    }

                    responses.add("File uploaded successfully: " + fileName);
                    images.append(fileName).append("/");
                } catch (IOException e) {
                    responses.add("Failed to upload the file " + file.getOriginalFilename() + ": " + e.getMessage());
                }
            } else {
                responses.add("File is empty: " + file.getOriginalFilename());
            }
        }

        product.setProductImages(String.valueOf(images));

        return responses;
    }

}
