package com.maybezone.productservice.domain.product.service;

import com.maybezone.productservice.domain.product.entity.Product;
import com.maybezone.productservice.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.maybezone.productservice.domain.product.productenum.MainCategory.ACCESSORIES;
import static com.maybezone.productservice.domain.product.productenum.ProductState.IN_STOCK;
import static com.maybezone.productservice.domain.product.productenum.ProductType.PRODUCT;
import static com.maybezone.productservice.domain.product.productenum.SubCategory.JEWELLERY;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ProductServiceImplStockTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @BeforeEach
    void beforeEach() {
        Product product = Product.builder()
                .name("product")
                .price(10000L)
                .image("image")
                .stockCount(100L)
                .likeCount(0L)
                .productState(IN_STOCK)
                .productType(PRODUCT)
                .mainCategory(ACCESSORIES)
                .subCategory(JEWELLERY)
                .build();

        productRepository.save(product);
    }

    @AfterEach
    void afterEach() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("재고 감소 테스트")
    void updateStockCountTest() {
        List<Product> findProducts = productRepository.findAll();
        Product product = findProducts.get(0);
        Long productId = product.getId();

        productService.updateStockCount(productId);

        Product findProduct = productRepository.findById(productId).orElseThrow();

        assertThat(findProduct.getStockCount()).isEqualTo(99);
    }

    @Test
    @DisplayName("동시에 100건 재고 감소 요청 테스트")
    void updateStockCount100RequestTest() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    List<Product> findProducts = productRepository.findAll();
                    Product product = findProducts.get(0);
                    Long productId = product.getId();
                    productService.updateStockCount(productId);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Product product = productRepository.findAll().get(0);

        assertThat(product.getStockCount()).isEqualTo(0);
    }

}
