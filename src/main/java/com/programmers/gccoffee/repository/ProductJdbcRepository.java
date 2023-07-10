package com.programmers.gccoffee.repository;

import com.programmers.gccoffee.model.Category;
import com.programmers.gccoffee.model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

import static com.programmers.gccoffee.JdbcUtils.toLocalDateTime;
import static com.programmers.gccoffee.JdbcUtils.toUUID;

@Repository
public class ProductJdbcRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Product save(Product product) {
        String sql = "insert into products (product_id, product_name, category, price, description, created_at, updated_at) values (?, ?, ?, ?, ?, ?, ?)";
        int update = jdbcTemplate.update(sql,
                product.getProductId().toString(),
                product.getProductName(),
                product.getCategory().toString(),
                product.getPrice(),
                product.getDescription(),
                product.getCreatedAt(),
                product.getUpdatedAt());
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted.");
        }
        return product;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("select * from products", productRowMapper());
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        String sql = "select * from products where product_id = ?";
        try {
            Product product = jdbcTemplate.queryForObject(sql, productRowMapper(), productId.toString());
            return Optional.of(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findByName(String productName) {
        String sql = "select * from products where product_name = ?";
        try {
            Product product = jdbcTemplate.queryForObject(sql, productRowMapper(), productName);
            return Optional.of(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return jdbcTemplate.query("select * from products where category = ?", productRowMapper(), category.toString());
    }

    @Override
    public void update(Product product) {
        String sql = "update products set product_name = ?, category = ?, price = ?, description = ?, created_at = ?, updated_at = ? where product_id = ?";
        int update = jdbcTemplate.update(sql,
                product.getProductName(),
                product.getCategory().toString(),
                product.getPrice(),
                product.getDescription(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getProductId().toString());
        if (update != 1) {
            throw new RuntimeException("Nothing was updated.");
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from products");
    }

    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> {
            var productId = toUUID(rs.getString("product_id"));
            var productName = rs.getString("product_name");
            var category = Category.valueOf(rs.getString("category"));
            var price = rs.getLong("price");
            var description = rs.getString("description");
            var createdAt = toLocalDateTime(rs.getTimestamp("created_at"));
            var updatedAt = toLocalDateTime(rs.getTimestamp("updated_at"));
            return new Product(productId, productName, category, price, description, createdAt, updatedAt);
        };
    }
}
