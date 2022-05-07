package com.example.yongbread.model;

import com.example.yongbread.model.product.Category;
import lombok.Builder;

import java.util.UUID;

public record OrderItem(UUID productId, Category category, long price, long quantity) {
    @Builder
    public OrderItem(UUID productId, Category category, long price, long quantity) {
        this.productId = productId;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderItem{");
        sb.append("productId=").append(productId);
        sb.append(", category=").append(category);
        sb.append(", price=").append(price);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }
}
