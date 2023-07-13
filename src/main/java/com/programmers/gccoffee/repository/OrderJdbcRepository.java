package com.programmers.gccoffee.repository;

import com.programmers.gccoffee.model.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class OrderJdbcRepository implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Order save(Order order) {
        String sql = "insert into orders (order_id, email, address, postcode, order_status, created_at, updated_at) values (?, ?, ?, ?, ?, ?, ?)";
        int update = jdbcTemplate.update(sql,
                order.getOrderId().toString(),
                order.getEmail(),
                order.getAddress(),
                order.getPostCode(),
                order.getOrderStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt());
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted.");
        }
        return order;
    }
}
