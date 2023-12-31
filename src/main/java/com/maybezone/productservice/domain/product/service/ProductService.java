package com.maybezone.productservice.domain.product.service;

import com.maybezone.productservice.domain.product.dto.response.ResponseProductDetailDto;
import com.maybezone.productservice.domain.product.dto.response.ResponseProductPageDto;

import java.util.List;

public interface ProductService {

    ResponseProductPageDto getNoOffsetMainPageProducts(Long productId);

    ResponseProductPageDto getSearchResult(List<String> mainCategory, List<String> subCategory, String searchWord, Long productId);

    ResponseProductDetailDto getDetailProduct(Long productId);

    void updateStockCount(Long ProductId);

}
