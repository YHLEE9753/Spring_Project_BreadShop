package com.example.yongbread.controller.dto;

import com.example.yongbread.model.product.Category;
import com.example.yongbread.model.product.ProductStatus;
import com.example.yongbread.model.product.SaleStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class ProductDto {
    private String productName;
    private String category;
    private String productStatus;
    private String saleStatus;
    private long price;
    private long salePrice;
    private long stock;

    @Builder
    public ProductDto() {
        this.productName = "null";
        this.category = "Bread";
        this.productStatus = "CLOSE";
        this.saleStatus = "NORMAL";
        this.price = 0;
        this.salePrice = 0;
        this.stock = 0;
    }
}
