package com.example.yongbread.controller;

import com.example.yongbread.controller.dto.OrderDto;
import com.example.yongbread.exception.CountInputException;
import com.example.yongbread.model.Order;
import com.example.yongbread.model.product.Category;
import com.example.yongbread.model.product.Product;
import com.example.yongbread.service.OrderService;
import com.example.yongbread.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final ProductService productService;
    private final OrderService orderService;

    public OrderController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping
    public String mainOrder(Model model){
        List<Product> breads = productService.findByCategory(Category.BREAD);
        List<Product> onSaleBreads = productService.findOnSale(breads);

        List<Product> cookies = productService.findByCategory(Category.COOKIE);
        List<Product> onSaleCookies = productService.findOnSale(cookies);

        model.addAttribute("breads", onSaleBreads);
        model.addAttribute("cookies", onSaleCookies);
        return "/orders/list";
    }

    @PostMapping
    public String mainOrder(@ModelAttribute OrderDto orderDto, Model model){
        try{
            orderService.productOrderCountValidation(orderDto.getBreadCount(), Category.BREAD);
            orderService.productOrderCountValidation(orderDto.getCookieCount(), Category.COOKIE);
        }catch (CountInputException e){
            return "/orders/error";
        }

        orderService.updateProduct(orderDto);
        Order order = orderService.createOrder(orderDto);
        model.addAttribute("order", order);
        model.addAttribute("orderItems", order.getOrderItems());

        return "/orders/detail";
    }
}
