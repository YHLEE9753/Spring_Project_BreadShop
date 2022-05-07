package com.example.yongbread.repository;

import com.example.yongbread.model.Email;
import com.example.yongbread.model.Order;
import com.example.yongbread.model.OrderItem;
import com.example.yongbread.model.OrderStatus;
import com.example.yongbread.model.product.Category;
import com.example.yongbread.testcontainer.AbstractContainerDatabaseTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderJdbcRepositoryTest  extends AbstractContainerDatabaseTest {

    @Autowired
    OrderRepository orderRepository;

    UUID id = UUID.randomUUID();

    @BeforeAll
    void beforeAll() {
        OrderItem orderItem1 = OrderItem.builder()
                .productId(UUID.randomUUID())
                .category(Category.BREAD)
                .quantity(10)
                .price(2000)
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .productId(UUID.randomUUID())
                .category(Category.COOKIE)
                .quantity(15)
                .price(1000)
                .build();

        Order order = Order.builder()
                .orderId(id)
                .email(new Email("dldydgns53@gmail.com"))
                .address("서울시 공릉동 243번지")
                .postcode("1234-5678")
                .orderItems(new ArrayList<>(Arrays.asList(orderItem1, orderItem2)))
                .orderStatus(OrderStatus.ACCEPTED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        orderRepository.insert(order);
    }

    @Test
    @DisplayName("Order 를 findById 로 찾아온다.")
    public void orderFindByIdTest () throws Exception{
        // given

        // when
        Order order = orderRepository.orderFindbyId(id).get();

        // then
        assertEquals(order.getOrderId(), id);
        assertEquals(order.getEmail().getAddress(), "dldydgns53@gmail.com");
        assertEquals(order.getOrderStatus(), OrderStatus.ACCEPTED);
    }

    @Test
    @DisplayName("OrderItems 를 findById 로 찾아온다.")
    public void orderItemsFindByIdTest () throws Exception{
        // given

        // when
        List<OrderItem> orderItems = orderRepository.orderItemsFindbyId(id);
        OrderItem item1 = orderItems.get(0);
        OrderItem item2 = orderItems.get(1);

        // then
        assertEquals(item1.category(), Category.BREAD);
        assertEquals(item1.quantity(), 10);
        assertEquals(item1.price(), 2000);

        assertEquals(item2.category(), Category.COOKIE);
        assertEquals(item2.quantity(), 15);
        assertEquals(item2.price(), 1000);
    }
}