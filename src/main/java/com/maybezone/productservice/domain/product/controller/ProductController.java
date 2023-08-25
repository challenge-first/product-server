package com.maybezone.productservice.domain.product.controller;

import com.maybezone.productservice.domain.product.dto.response.ResponseDataDto;
import com.maybezone.productservice.domain.product.dto.response.ResponseProductDetailDto;
import com.maybezone.productservice.domain.product.dto.response.ResponseProductDto;
import com.maybezone.productservice.domain.product.dto.response.ResponseStockDto;
import com.maybezone.productservice.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.*;

@CrossOrigin("http://127.0.0.1:5500")
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/main")
    public ResponseEntity<ResponseDataDto<Page<ResponseProductDto>>> getMainPageProducts(Pageable pageable) {
        Page<ResponseProductDto> responseProductDtos = productService.getMainPageProducts(pageable);
        ResponseDataDto<Page<ResponseProductDto>> responseDataDto = new ResponseDataDto<>(responseProductDtos);

        return ResponseEntity.ok(responseDataDto);
    }

    @GetMapping
    ResponseEntity<ResponseDataDto<Page<ResponseProductDto>>> getSearchResult(@RequestParam(name = "maincategory", required = false) List<String> mainCategories,
                                                                              @RequestParam(name = "subcategory", required = false) List<String> subCategories,
                                                                              @RequestParam(name = "searchword", required = false) String searchWord,
                                                                              @PageableDefault(sort = "id", direction = DESC, size = 4) Pageable pageable) {
        Page<ResponseProductDto> searchResult = productService.getSearchResult(mainCategories, subCategories, searchWord, pageable);
        ResponseDataDto<Page<ResponseProductDto>> responseDataDto = new ResponseDataDto<>(searchResult);

        return ResponseEntity.ok(responseDataDto);
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
