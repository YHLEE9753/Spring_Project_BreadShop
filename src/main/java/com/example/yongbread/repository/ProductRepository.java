package com.example.yongbread.repository;

import com.example.yongbread.model.product.Category;
import com.example.yongbread.model.product.ProductStatus;
import com.example.yongbread.model.product.SaleStatus;
import com.example.yongbread.model.product.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ProductRepository {
    List<Product> findAll();

    Product insert(Product product);

    Product update(Product product);

    Optional<Product> findById(UUID productId);

    Optional<Product> findByName(String productName);

    List<Product> findByCategory(Category category);

    List<Product> findBySaleStatus(SaleStatus saleStatus);

    List<Product> findByProductStatus(ProductStatus productStatus);

    int count();

    void deleteAll();

    void deleteById(UUID productId);
}
