package com.maybezone.productservice.domain.product.dto.response;


import com.maybezone.productservice.domain.product.productenum.ProductState;
import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class ResponseProductDetailDto {

    private String name;
    private Long price;
    private Long stockCount;
    private ProductState productState;
    private String image;
    private Long likeCount;

}
