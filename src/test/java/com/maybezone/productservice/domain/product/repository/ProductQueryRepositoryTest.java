package com.maybezone.productservice.domain.product.repository;

import com.maybezone.productservice.domain.product.entity.Product;
import com.maybezone.productservice.domain.product.productenum.MainCategory;
import com.maybezone.productservice.domain.product.productenum.SubCategory;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static com.maybezone.productservice.domain.product.productenum.MainCategory.*;
import static com.maybezone.productservice.domain.product.productenum.MainCategory.ACCESSORIES;
import static com.maybezone.productservice.domain.product.productenum.ProductState.IN_STOCK;
import static com.maybezone.productservice.domain.product.productenum.ProductType.PRODUCT;
import static com.maybezone.productservice.domain.product.productenum.SubCategory.*;
import static com.maybezone.productservice.domain.product.productenum.SubCategory.BACKPACKS;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.*;

@SpringBootTest
@Transactional
class ProductQueryRepositoryTest {

    @Autowired
    ProductQueryRepository productQueryRepository;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void beforeEach() {
        for (long i = 1; i <= 20; i++) {
            if (i % 2 == 0) {
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

            if (i % 2 == 1) {
                Product product = Product.builder()
                        .name("name" + i)
                        .price(i)
                        .image("image" + i)
                        .stockCount(i)
                        .likeCount(i)
                        .productState(IN_STOCK)
                        .productType(PRODUCT)
                        .mainCategory(MENS_SHOES)
                        .subCategory(KIDS_SHOES)
                        .build();

                productRepository.save(product);
            }
        }
    }

    @Test
    @DisplayName("조건 검색 테스트 - 조건 없음")
    void noSearchConditionProductsTest() {
        List<MainCategory> mainCategories = new ArrayList<>();
        List<SubCategory> subCategories = new ArrayList<>();

        Pageable pageable = PageRequest.of(0, 4, DESC, "id");
        List<Product> products = productQueryRepository.searchProducts(mainCategories, subCategories, "", pageable);

        assertThat(products.size()).isEqualTo(4);
        assertThat(products.get(0).getMainCategory()).isEqualTo(MENS_SHOES);
        assertThat(products.get(1).getMainCategory()).isEqualTo(ACCESSORIES);
        assertThat(products.get(2).getMainCategory()).isEqualTo(MENS_SHOES);
        assertThat(products.get(3).getMainCategory()).isEqualTo(ACCESSORIES);
    }

    @Test
    @DisplayName("조건 검색 테스트 - 메인 카테고리")
    void mainCategorySearchConditionProductsTest() {
        List<MainCategory> mainCategories = new ArrayList<>();
        mainCategories.add(MENS_SHOES);
        List<SubCategory> subCategories = new ArrayList<>();

        Pageable pageable = PageRequest.of(0, 4, DESC, "id");
        List<Product> products = productQueryRepository.searchProducts(mainCategories, subCategories, "", pageable);

        assertThat(products.size()).isEqualTo(4);
        assertThat(products.get(0).getMainCategory()).isEqualTo(MENS_SHOES);
        assertThat(products.get(1).getMainCategory()).isEqualTo(MENS_SHOES);
        assertThat(products.get(2).getMainCategory()).isEqualTo(MENS_SHOES);
        assertThat(products.get(3).getMainCategory()).isEqualTo(MENS_SHOES);
    }

    @Test
    @DisplayName("조건 검색 테스트 - 서브 카테고리")
    void subCategorySearchConditionProductsTest() {
        List<MainCategory> mainCategories = new ArrayList<>();
        List<SubCategory> subCategories = new ArrayList<>();
        subCategories.add(BACKPACKS);

        Pageable pageable = PageRequest.of(0, 4, DESC, "id");
        List<Product> products = productQueryRepository.searchProducts(mainCategories, subCategories, "", pageable);

        assertThat(products.size()).isEqualTo(4);
        assertThat(products.get(0).getSubCategory()).isEqualTo(BACKPACKS);
        assertThat(products.get(1).getSubCategory()).isEqualTo(BACKPACKS);
        assertThat(products.get(2).getSubCategory()).isEqualTo(BACKPACKS);
        assertThat(products.get(3).getSubCategory()).isEqualTo(BACKPACKS);
    }

    @Test
    @DisplayName("조건 검색 테스트 - 검색어")
    void searchWordConditionProductsTest() {
        List<MainCategory> mainCategories = new ArrayList<>();
        List<SubCategory> subCategories = new ArrayList<>();

        Pageable pageable = PageRequest.of(0, 4, DESC, "id");
        List<Product> products = productQueryRepository.searchProducts(mainCategories, subCategories, "10", pageable);

        assertThat(products.size()).isEqualTo(1);
        assertThat(products.get(0).getName()).isEqualTo("name10");
    }

}