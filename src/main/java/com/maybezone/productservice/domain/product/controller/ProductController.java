package com.maybezone.productservice.domain.product.controller;

import com.maybezone.productservice.domain.product.dto.response.ResponseDataDto;
import com.maybezone.productservice.domain.product.dto.response.ResponseProductDetailDto;
import com.maybezone.productservice.domain.product.dto.response.ResponseProductDto;
import com.maybezone.productservice.domain.product.entity.Product;
import com.maybezone.productservice.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/main")
    public ResponseEntity<ResponseDataDto<List<ResponseProductDto>>> getMainPageProducts(Pageable pageable) {
        List<ResponseProductDto> responseProductDtos = productService.getMainPageProducts(pageable);
        ResponseDataDto<List<ResponseProductDto>> responseDataDto = new ResponseDataDto<>(responseProductDtos);

        return ResponseEntity.ok(responseDataDto);
    }

    @GetMapping
    ResponseEntity<ResponseDataDto<List<ResponseProductDto>>> getSearchResult(@RequestParam(name = "maincategory", required = false) List<String> mainCategories,
                                                                              @RequestParam(name = "subcategory", required = false) List<String> subCategories,
                                                                              @RequestParam(name = "searchword", required = false) String searchWord,
                                                                              Pageable pageable) {
        List<ResponseProductDto> searchResult = productService.getSearchResult(mainCategories, subCategories, searchWord, pageable);
        ResponseDataDto<List<ResponseProductDto>> responseDataDto = new ResponseDataDto<>(searchResult);

        return ResponseEntity.ok(responseDataDto);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDataDto<ResponseProductDetailDto>> getDetailProduct(@PathVariable Long productId) {
        ResponseProductDetailDto responseproductdetaildto = productService.getDetailProduct(productId);
        ResponseDataDto<ResponseProductDetailDto> responseDataDto = new ResponseDataDto<>(responseproductdetaildto);

        return ResponseEntity.ok(responseDataDto);
    }

}
