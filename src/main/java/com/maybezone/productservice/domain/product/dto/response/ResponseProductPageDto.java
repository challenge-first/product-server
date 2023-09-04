package com.maybezone.productservice.domain.product.dto.response;

import lombok.*;

import java.util.List;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class ResponseProductPageDto {

    private List<ResponseProductDto> data;
    private Long lastProductId;

}
