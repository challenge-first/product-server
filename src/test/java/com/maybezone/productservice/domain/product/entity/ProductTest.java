package com.maybezone.productservice.domain.product.entity;

import com.maybezone.productservice.domain.product.productenum.MainCategory;
import com.maybezone.productservice.domain.product.productenum.ProductState;
import com.maybezone.productservice.domain.product.productenum.ProductType;
import com.maybezone.productservice.domain.product.productenum.SubCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.maybezone.productservice.domain.product.productenum.MainCategory.*;
import static com.maybezone.productservice.domain.product.productenum.ProductState.*;
import static com.maybezone.productservice.domain.product.productenum.ProductType.*;
import static com.maybezone.productservice.domain.product.productenum.SubCategory.*;
import static org.assertj.core.api.Assertions.*;

class ProductTest {

    private Product product;

    @BeforeEach
    void beforeEach() {
        product = Product.builder()
                .stockCount(1L)
                .build();
    }

    @Test
    @DisplayName("상품 재고 감소 테스트")
    void updateStockCountTest() {
        Long stockCount = product.updateStockCount();

        assertThat(stockCount).isEqualTo(0);
    }

}