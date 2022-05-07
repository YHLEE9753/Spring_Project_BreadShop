package com.example.yongbread.repository;

import com.example.yongbread.model.Order;
import com.example.yongbread.model.OrderItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order insert(Order order);

    Optional<Order> orderFindbyId(UUID orderId);

    List<OrderItem> orderItemsFindbyId(UUID orderId);
}
