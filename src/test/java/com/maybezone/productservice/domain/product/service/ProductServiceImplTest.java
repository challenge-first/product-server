package com.maybezone.productservice.domain.product.service;

import com.maybezone.productservice.domain.product.dto.response.ResponseProductDetailDto;
import com.maybezone.productservice.domain.product.dto.response.ResponseProductPageDto;
import com.maybezone.productservice.domain.product.entity.Product;
import com.maybezone.productservice.domain.product.repository.ProductQueryRepository;
import com.maybezone.productservice.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.maybezone.productservice.domain.product.productenum.MainCategory.ACCESSORIES;
import static com.maybezone.productservice.domain.product.productenum.ProductState.IN_STOCK;
import static com.maybezone.productservice.domain.product.productenum.ProductType.PRODUCT;
import static com.maybezone.productservice.domain.product.productenum.SubCategory.BACKPACKS;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private List<Product> productList = new ArrayList<>();

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductQueryRepository productQueryRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @BeforeEach
    void beforeEach() {
        for (long i = 20; i > 0; i--) {
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

            productList.add(product);
        }
    }

    @Test
    @DisplayName("메인 페이지 상품 전체조회 테스트")
    void getMainPageProductsTest() {
        when(productQueryRepository.findNoOffsetPageById(null))
                .thenReturn(productList);

        ResponseProductPageDto responseProductPageDto = productService.getNoOffsetMainPageProducts(null);

        assertThat(responseProductPageDto.getData().size()).isEqualTo(20);
    }

    @Test
    @DisplayName("조건 검색 테스트")
    void getSearchResultTest() {
        when(productQueryRepository.searchProducts(any(), any(), any(), any())).thenReturn(productList);

        ResponseProductPageDto searchResult = productService.getSearchResult(anyList(), anyList(), any(), any());

        assertThat(searchResult.getData().size()).isEqualTo(20);
    }

    @Test
    @DisplayName("상품 상세 조회 테스트")
    void getDetailProductTest() {
        when(productRepository.findById(any()))
                .thenReturn(Optional.ofNullable(productList.get(0)));

        ResponseProductDetailDto detailProduct = productService.getDetailProduct(any());

        assertThat(detailProduct.getProductState()).isEqualTo(IN_STOCK);
    }

}