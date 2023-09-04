package com.maybezone.productservice.domain.product.controller;

import com.maybezone.productservice.domain.product.dto.response.*;
import com.maybezone.productservice.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/main")
    public ResponseEntity<ResponseProductPageDto> getNoOffsetMainPageProducts(@RequestParam(name = "lastproductid", required = false) Long productId) {
        ResponseProductPageDto responseProductPageDto = productService.getNoOffsetMainPageProducts(productId);

        return ResponseEntity.ok(responseProductPageDto);
    }

    @GetMapping
    ResponseEntity<ResponseProductPageDto> getSearchResult(@RequestParam(name = "maincategory", required = false) List<String> mainCategories,
                                                           @RequestParam(name = "subcategory", required = false) List<String> subCategories,
                                                           @RequestParam(name = "searchword", required = false) String searchWord,
                                                           @RequestParam(name = "lastproductid", required = false) Long productId) {
        ResponseProductPageDto responseProductPageDto = productService.getSearchResult(mainCategories, subCategories, searchWord, productId);

        return ResponseEntity.ok(responseProductPageDto);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDataDto<ResponseProductDetailDto>> getDetailProduct(@PathVariable Long productId) {
        ResponseProductDetailDto responseproductdetaildto = productService.getDetailProduct(productId);
        ResponseDataDto<ResponseProductDetailDto> responseDataDto = new ResponseDataDto<>(responseproductdetaildto);

        return ResponseEntity.ok(responseDataDto);
    }

    @GetMapping("/stock/{productId}")
    public ResponseEntity<ResponseStockDto> getStock(@PathVariable Long productId) {
        ResponseStockDto responseStockDto = ResponseStockDto.builder()
                .stockCount(productService.getDetailProduct(productId).getStockCount())
                .build();

        return ResponseEntity.ok(responseStockDto);
    }
}
