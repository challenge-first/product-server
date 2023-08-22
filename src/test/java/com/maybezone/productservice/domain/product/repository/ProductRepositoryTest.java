package com.maybezone.productservice.domain.product.repository;

import com.maybezone.productservice.domain.product.entity.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.maybezone.productservice.domain.product.productenum.MainCategory.*;
import static com.maybezone.productservice.domain.product.productenum.ProductState.*;
import static com.maybezone.productservice.domain.product.productenum.ProductType.*;
import static com.maybezone.productservice.domain.product.productenum.SubCategory.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.*;

@SpringBootTest
@Transactional
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void beforeEach() {
        for (long i = 1; i <= 10; i++) {
            Product product = Product.builder()
                    .name("name" + i)
                    .price(i)
                    .image("image" + i)
                    .stockCount(i)
                    .likeCount(i)
                    .productState(IN_STOCK)
                    .productType(PRODUCT)
                    .mainCategory(ACCESSORIES)
                    .subCategory(BACKPACKS)
                    .build();

            productRepository.save(product);
        }
    }

    @Test
    @DisplayName("메인 페이지 상품 전체조회 테스트")
    void findTop4ByOrderByIdDescTest() {
        Pageable pageable = PageRequest.of(1, 4, DESC, "id");
        List<Product> findProducts = productRepository.findTop4ByOrderByIdDesc(pageable);

        assertThat(findProducts.size()).isEqualTo(4);
        assertThat(findProducts.get(0).getName()).isEqualTo("name6");
        assertThat(findProducts.get(1).getImage()).isEqualTo("image5");
        assertThat(findProducts.get(2).getPrice()).isEqualTo(4L);
        assertThat(findProducts.get(3).getStockCount()).isEqualTo(3L);
    }

}