package com.example.yongbread.controller;

import com.example.yongbread.controller.dto.ProductDto;
import com.example.yongbread.model.product.Product;
import com.example.yongbread.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String mainAdmin(Model model){
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "/admin/list";
    }

    @GetMapping("/register")
    public String productRegister(){
        return "/admin/register";
    }

    @PostMapping("/register")
    public String productRegister(@ModelAttribute ProductDto productDto){
        Product product = new Product(productDto);
        productService.createProduct(product);
        return "redirect:/admin";
    }

    @GetMapping("/{productId}")
    public String productDetail(@PathVariable UUID productId,Model model){
        Product product = productService.findById(productId);
        model.addAttribute("product", product);
        return "/admin/detail";
    }

    @GetMapping("/update/{productId}")
    public String productUpdate(@PathVariable UUID productId,Model model){
        Product product = productService.findById(productId);
        model.addAttribute("product", product);
        return "/admin/update";
    }

    @PostMapping("/update/{productId}")
    public String productUpdate(@ModelAttribute ProductDto productDto, @PathVariable UUID productId){
        Product product = new Product(productDto, productId);
        productService.update(product);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{productId}")
    public String productDeleteById(@PathVariable UUID productId){
        System.out.println(productId);
        productService.deleteById(productId);
        return "redirect:/admin";
    }

    @GetMapping("/delete")
    public String productDeleteAll(){
        productService.deleteAll();
        return "redirect:/admin";
    }
}
