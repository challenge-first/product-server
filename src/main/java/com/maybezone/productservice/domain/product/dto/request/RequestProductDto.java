package com.maybezone.productservice.domain.product.dto.request;

import com.maybezone.productservice.domain.product.productenum.MainCategory;
import com.maybezone.productservice.domain.product.productenum.SubCategory;
import lombok.*;

import java.util.List;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class RequestProductDto {

    private MainCategory mainCategory;
    private List<SubCategory> subCategories;
    private String searchWord;

}
