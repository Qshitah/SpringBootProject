package com.login.practice.Entity;

import com.login.practice.signUp.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;


    @Column(name = "SKU")
    private String sku;

    @Column(name = "ProductName")
    private String productName;

    @Column(name = "Description")
    private String description;

    @Column(name = "RegularPrice")
    private Float regularPrice;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE
            ,CascadeType.PERSIST
            ,CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;


    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE
            ,CascadeType.PERSIST
            ,CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "SalePrice")
    private Float salePrice;

    @Column(name = "CostPrice")
    private Float costPrice;

    @Column(name = "StockQuantity")
    private Integer stockQuantity;

    @Column(name = "Weight")
    private Float weight;

    @Column(name = "ReleaseDate")
    private Date releaseDate;

    @Column(name = "IsFeatured")
    private int isFeatured;

    @Column(name = "IsActive")
    private int isActive;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "json",name = "ProductImages")
    private String productImages;

    @Column(name = "VariantColors")
    private String variantColors;

    @Column(name = "VariantSizes")
    private String variantSizes;

    public Product(){

    }

    public Product(int id, String sku, String productName,
                   String description,
                   Float regularPrice1, Float salePrice,
                   Float costPrice, Integer stockQuantity,
                   Float weight, String productImages,
                   String variantColors, String variantSizes) {
        Id = id;
        this.sku = sku;
        this.productName = productName;
        this.description = description;
        this.regularPrice = regularPrice1;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.stockQuantity = stockQuantity;
        this.weight = weight;
        this.productImages = productImages;
        this.variantColors = variantColors;
        this.variantSizes = variantSizes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(Float regularPrice) {
        this.regularPrice = regularPrice;
    }

    public Float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Float salePrice) {
        this.salePrice = salePrice;
    }

    public Float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Float costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(int isFeatured) {
        this.isFeatured = isFeatured;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getProductImages() {
        return productImages;
    }

    public void setProductImages(String productImages) {
        this.productImages = productImages;
    }

    public String getVariantColors() {
        return variantColors;
    }

    public void setVariantColors(String variantColors) {
        this.variantColors = variantColors;
    }

    public String getVariantSizes() {
        return variantSizes;
    }

    public void setVariantSizes(String variantSizes) {
        this.variantSizes = variantSizes;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "Id=" + Id +
                ", sku='" + sku + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", regularPrice=" + regularPrice +
                ", salePrice=" + salePrice +
                ", costPrice=" + costPrice +
                ", stockQuantity=" + stockQuantity +
                ", weight=" + weight +
                ", releaseDate=" + releaseDate +
                ", isFeatured=" + isFeatured +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", productImages='" + productImages + '\'' +
                ", variantColors='" + variantColors + '\'' +
                '}';
    }
}

