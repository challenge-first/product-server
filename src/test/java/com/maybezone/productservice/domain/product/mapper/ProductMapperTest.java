package com.maybezone.productservice.domain.product.mapper;

import com.maybezone.productservice.domain.product.dto.response.ResponseProductDto;
import com.maybezone.productservice.domain.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.maybezone.productservice.domain.product.productenum.ProductState.*;
import static org.assertj.core.api.Assertions.*;

class ProductMapperTest {

    private final ProductMapper productInstance = ProductMapper.PRODUCT_INSTANCE;

    @Test
    @DisplayName("entityToResponseProductDto 매핑 테스트")
    public void entityToResponseProductDtoTest() {
        Product product = Product.builder()
                .id(1L)
                .name("name")
                .price(100L)
                .image("www.image.com")
                .likeCount(0L)
                .productState(IN_STOCK)
                .build();

        ResponseProductDto responseProductDto = productInstance.entityToResponseProductDto(product);

        assertThat(responseProductDto.getId()).isEqualTo(1L);
        assertThat(responseProductDto.getName()).isEqualTo("name");
        assertThat(responseProductDto.getPrice()).isEqualTo(100L);
        assertThat(responseProductDto.getImage()).isEqualTo("www.image.com");
        assertThat(responseProductDto.getLikeCount()).isEqualTo(0L);
        assertThat(responseProductDto.getProductState()).isEqualTo(IN_STOCK);
    }

}