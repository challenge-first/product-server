package com.maybezone.productservice.domain.product.service;

import com.maybezone.productservice.domain.product.dto.response.ResponseProductDetailDto;
import com.maybezone.productservice.domain.product.dto.response.ResponseProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<ResponseProductDto> getMainPageProducts(Pageable pageable);

    Page<ResponseProductDto> getSearchResult(List<String> mainCategory, List<String> subCategory, String searchWord, Pageable pageable);

    ResponseProductDetailDto getDetailProduct(Long productId);

}
