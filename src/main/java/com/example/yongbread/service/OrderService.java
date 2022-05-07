package com.example.yongbread.service;

import com.example.yongbread.controller.dto.OrderDto;
import com.example.yongbread.exception.CountInputException;
import com.example.yongbread.model.Order;
import com.example.yongbread.model.OrderItem;
import com.example.yongbread.model.OrderStatus;
import com.example.yongbread.model.product.Category;
import com.example.yongbread.model.product.Product;
import com.example.yongbread.repository.OrderRepository;
import com.example.yongbread.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order orderFindById(UUID orderId){
        Order order = orderRepository.orderFindbyId(orderId).get();
        List<OrderItem> orderItems = orderRepository.orderItemsFindbyId(orderId);
        order.setOrderItems(orderItems);
        return order;
    }

    public void updateProduct(OrderDto orderDto) {
        updateProductStock(orderDto.getBreadCount(), Category.BREAD);
        updateProductStock(orderDto.getCookieCount(), Category.COOKIE);
    }

    private void updateProductStock(List<Long> counts, Category category) {
        List<Product> products = productRepository.findByCategory(category);
        for (int i = 0; i < counts.size(); i++) {
            Product product = products.get(i);
            product.changeStock(counts.get(i));
            productRepository.update(product);
        }
    }


    public Order createOrder(OrderDto orderDto) {
        List<OrderItem> orderItem = createOrderItem(orderDto);

        Order order = Order.builder()
                .orderId(UUID.randomUUID())
                .email(orderDto.getEmail())
                .address(orderDto.getAddress())
                .postcode(orderDto.getPostcode())
                .orderItems(orderItem)
                .orderStatus(OrderStatus.ACCEPTED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return orderRepository.insert(order);
    }

    private List<OrderItem> createOrderItem(OrderDto orderDto) {
        List<OrderItem> orderItems = new ArrayList<>();
        List<Product> breads = productRepository.findByCategory(Category.BREAD);
        List<Long> breadCount = orderDto.getBreadCount();

        for (int i = 0; i < breadCount.size(); i++) {
            OrderItem item = OrderItem.builder()
                    .productId(breads.get(i).getProductId())
                    .category(Category.BREAD)
                    .price(breads.get(i).getRealPrice())
                    .quantity(breadCount.get(i))
                    .build();
            orderItems.add(item);
        }

        List<Product> cookies = productRepository.findByCategory(Category.COOKIE);
        List<Long> cookieCount = orderDto.getCookieCount();

        for (int i = 0; i < cookieCount.size(); i++) {
            orderItems.add(OrderItem.builder()
                    .productId(cookies.get(i).getProductId())
                    .category(Category.COOKIE)
                    .price(cookies.get(i).getRealPrice())
                    .quantity(cookieCount.get(i))
                    .build());
        }
        return orderItems;
    }

    public void productOrderCountValidation(List<Long> count, Category category) {
        List<Product> products = productRepository.findByCategory(category);
        List<Long> productCount = products.stream().map((product) -> product.getStock()).collect(Collectors.toList());
        for (int i = 0; i < count.size(); i++) {
            if (count.get(i) > productCount.get(i)) {
                throw new CountInputException();
            }
        }
    }
}
