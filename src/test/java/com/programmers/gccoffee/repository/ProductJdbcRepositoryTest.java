package com.programmers.gccoffee.repository;

import com.programmers.gccoffee.model.Category;
import com.programmers.gccoffee.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class ProductJdbcRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    private Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);

    @Order(1)
    @Test
    @DisplayName("상품을 추가할 수 있다.")
    void testSave() {
        productRepository.save(newProduct);
        List<Product> result = productRepository.findAll();
        assertThat(result).isNotEmpty();
    }

    @Order(2)
    @Test
    @DisplayName("상품을 아이디로 조회할 수 있다.")
    void testFindById() {
        Optional<Product> result = productRepository.findById(newProduct.getProductId());
        assertThat(result).isNotNull();
    }

    @Order(3)
    @Test
    @DisplayName("상품을 이름으로 조회할 수 있다.")
    void testFindByName() {
        Optional<Product> result = productRepository.findByName(newProduct.getProductName());
        assertThat(result).isNotNull();
    }

    @Order(4)
    @Test
    @DisplayName("상품들을 카테고리로 조회할 수 있다.")
    void testFindByCategory() {
        List<Product> result = productRepository.findByCategory(Category.COFFEE_BEAN_PACKAGE);
        assertThat(result).isNotNull();
    }

    @Order(5)
    @Test
    @DisplayName("상품을 수정할 수 있다.")
    void testUpdate() {
        newProduct.setProductName("updated-product");
        newProduct.setPrice(2000L);
        productRepository.update(newProduct);

        Optional<Product> result = productRepository.findById(newProduct.getProductId());
        assertThat(result.get()).isEqualTo(newProduct);
    }

    @Order(6)
    @Test
    @DisplayName("상품들을 모두 삭제한다.")
    void testDeleteAll() {
        productRepository.deleteAll();
        List<Product> result = productRepository.findAll();
        assertThat(result).isEmpty();
    }
}