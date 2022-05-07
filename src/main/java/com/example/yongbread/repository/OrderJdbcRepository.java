package com.example.yongbread.repository;

import com.example.yongbread.model.Email;
import com.example.yongbread.model.Order;
import com.example.yongbread.model.OrderItem;
import com.example.yongbread.model.OrderStatus;
import com.example.yongbread.model.product.Category;
import com.example.yongbread.util.RepositoryUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class OrderJdbcRepository implements OrderRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional // 하나라도 에러나면 rollback 하게
    public Order insert(Order order) {
        jdbcTemplate.update("INSERT INTO orders(order_id, email, address, postcode, order_status, created_at, updated_at) " +
                        "VALUES(UUID_TO_BIN(:orderId), :email, :address, :postcode, :orderStatus, :createdAt, :updatedAt)",
                toOrderParamMap(order));
        order.getOrderItems()
                .forEach(item ->
                        jdbcTemplate.update("INSERT INTO order_items(order_id, product_id, category, price, quantity, created_at, updated_at) " +
                                        "VALUES(UUID_TO_BIN(:orderId), UUID_TO_BIN(:productId), :category, :price, :quantity, :createdAt, :updatedAt)",
                                toOrderItemParamMap(order.getOrderId(), order.getCreatedAt(), order.getUpdatedAt(), item)));
        return order;
    }

    @Override
    public Optional<Order> orderFindbyId(UUID orderId) {
        final String idQuery = "SELECT * FROM orders WHERE order_id = UUID_TO_BIN(:order_id)";
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject(idQuery, Collections.singletonMap("order_id", orderId.toString().getBytes()), orderRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<OrderItem> orderItemsFindbyId(UUID orderId) {
        final String idQuery = "SELECT * FROM order_items WHERE order_id = UUID_TO_BIN(:order_id)";
        return jdbcTemplate.query(idQuery, Collections.singletonMap("order_id", orderId.toString().getBytes()), orderItemRowMapper);
    }


    private final Map<String, Object> toOrderParamMap(Order order) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", order.getOrderId().toString().getBytes());
        paramMap.put("email", order.getEmail().getAddress());
        paramMap.put("address", order.getAddress());
        paramMap.put("postcode", order.getPostcode());
        paramMap.put("orderStatus", order.getOrderStatus().toString());
        paramMap.put("createdAt", order.getCreatedAt());
        paramMap.put("updatedAt", order.getUpdatedAt());

        return paramMap;
    }

    private final RowMapper<Order> orderRowMapper = (resultSet, i) -> {
        var orderId = RepositoryUtil.toUUID(resultSet.getBytes("order_id"));
        var email = new Email(resultSet.getString("email"));
        var address = resultSet.getString("address");
        var postcode = resultSet.getString("postcode");
        var orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
        var createdAt = RepositoryUtil.toLocalDateTime(resultSet.getTimestamp("created_at"));
        var updatedAt = RepositoryUtil.toLocalDateTime(resultSet.getTimestamp("updated_at"));

        return Order.builder()
                .orderId(orderId)
                .email(email)
                .address(address)
                .postcode(postcode)
                .orderStatus(orderStatus)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    };

    private final RowMapper<OrderItem> orderItemRowMapper = (resultSet, i) -> {
        var productId = RepositoryUtil.toUUID(resultSet.getBytes("product_id"));
        var category = Category.valueOf(resultSet.getString("category"));
        var price = resultSet.getLong("price");
        var quantity = resultSet.getInt("quantity");

        return OrderItem.builder()
                .productId(productId)
                .category(category)
                .price(price)
                .quantity(quantity)
                .build();
    };

    private final Map<String, Object> toOrderItemParamMap(UUID orderId, LocalDateTime createAt, LocalDateTime updatedAt, OrderItem item) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId.toString().getBytes());
        paramMap.put("productId", item.productId().toString().getBytes());
        paramMap.put("category", item.category().toString());
        paramMap.put("price", item.price());
        paramMap.put("quantity", item.quantity());
        paramMap.put("createdAt", createAt);
        paramMap.put("updatedAt", updatedAt);

        return paramMap;
    }
}
