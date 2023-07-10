package com.programmers.gccoffee.repository;

import com.programmers.gccoffee.model.Category;
import com.programmers.gccoffee.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    List<Product> findAll();

    Optional<Product> findById(UUID productId);

    Optional<Product> findByName(String productName);

    List<Product> findByCategory(Category category);

    void update(Product product);

    void deleteAll();
}