package com.maybezone.productservice.domain.product.dto.response;

import com.maybezone.productservice.domain.product.productenum.ProductState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class ResponseProductDto {

    private Long id;
    private String name;
    private Long price;
    private ProductState productState;
    private Long likeCount;
    private String image;

}
