package com.example.yongbread.model.product;

import com.example.yongbread.controller.dto.ProductDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Product {
    private final UUID productId;
    private final String productName;
    private final Category category;
    private ProductStatus productStatus;
    private SaleStatus saleStatus;
    private long price;
    private long salePrice;
    private long stock;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private long realPrice;
    private long orderCount;

    @Builder
    public Product(UUID productId, String productName, Category category, ProductStatus productStatus, SaleStatus saleStatus, long price, long salePrice, long stock, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.productStatus = productStatus;
        this.saleStatus = saleStatus;
        this.price = price;
        this.salePrice = salePrice;
        this.stock = stock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.realPrice = price-salePrice;
        this.orderCount = 0;
    }

    public Product(ProductDto productDto){
        this.productId = UUID.randomUUID();
        this.productName = productDto.getProductName();
        this.category = Category.valueOf(productDto.getCategory());
        this.productStatus = ProductStatus.valueOf(productDto.getProductStatus());
        this.saleStatus = SaleStatus.valueOf(productDto.getSaleStatus());
        this.price = productDto.getPrice();
        this.salePrice = productDto.getSalePrice();
        this.stock = productDto.getStock();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.realPrice = price-salePrice;
        this.orderCount = 0;
    }

    public Product(ProductDto productDto, UUID id){
        this.productId = id;
        this.productName = productDto.getProductName();
        this.category = Category.valueOf(productDto.getCategory());
        this.productStatus = ProductStatus.valueOf(productDto.getProductStatus());
        this.saleStatus = SaleStatus.valueOf(productDto.getSaleStatus());
        this.price = productDto.getPrice();
        this.salePrice = productDto.getSalePrice();
        this.stock = productDto.getStock();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.realPrice = price-salePrice;
        this.orderCount = 0;
    }

    public void changeProductStatus(ProductStatus productStatus){
        this.productStatus = productStatus;
    }

    public void changeStock(Long saleCount){
        this.stock -= saleCount;
    }

    public void changeOrderCount(Long count){
        this.orderCount = count;
    }
}
