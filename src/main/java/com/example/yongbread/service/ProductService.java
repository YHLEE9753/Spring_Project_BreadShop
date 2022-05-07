package com.example.yongbread.service;

import com.example.yongbread.model.product.Category;
import com.example.yongbread.model.product.Product;
import com.example.yongbread.model.product.ProductStatus;
import com.example.yongbread.model.product.SaleStatus;
import com.example.yongbread.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.insert(product);
    }

    public Product findById(UUID productId) {
        Product getProduct = productRepository.findById(productId).get();
        if (getProduct == null) {
            //validation
        }
        return getProduct;
    }

    public Product update(Product product){
        Product updatedProduct = productRepository.update(product);
        return updatedProduct;
    }

    public void deleteById(UUID productId){
        productRepository.deleteById(productId);
    }

    public void deleteAll() {
        productRepository.deleteAll();
    }

    public Product findByName(String name) {
        Product getProduct = productRepository.findByName(name).get();
        if (getProduct == null) {
            //validation
        }
        return getProduct;
    }

    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> findOnSale(List<Product> products){
        return products.stream()
                .filter(product -> product.getProductStatus() == ProductStatus.ON_SALE)
                .collect(Collectors.toList());
    }

    public List<Product> findBySaleStatus(SaleStatus saleStatus) {
        return productRepository.findBySaleStatus(saleStatus);
    }

    public List<Product> findByProductStatus(ProductStatus productStatus) {
        return productRepository.findByProductStatus(productStatus);
    }
}