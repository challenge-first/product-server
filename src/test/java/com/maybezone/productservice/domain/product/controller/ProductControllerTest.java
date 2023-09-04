package com.maybezone.productservice.domain.product.controller;

import com.maybezone.productservice.domain.product.dto.response.ResponseProductDetailDto;
import com.maybezone.productservice.domain.product.dto.response.ResponseProductDto;
import com.maybezone.productservice.domain.product.dto.response.ResponseProductPageDto;
import com.maybezone.productservice.domain.product.service.ProductService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    private List<ResponseProductDto> responseProductDtos = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        for (int i = 20; i > 0; i--) {
            ResponseProductDto responseProductDto = ResponseProductDto.builder().build();
            responseProductDtos.add(responseProductDto);
        }
    }

    @Test
    @DisplayName("메인 페이지 상품 전체 조회 테스트")
    void getMainPageProductsTest() throws Exception {
        ResponseProductPageDto responseProductPageDto = ResponseProductPageDto.builder()
                .data(responseProductDtos)
                .lastProductId(1L)
                .build();

        when(productService.getNoOffsetMainPageProducts(any()))
                .thenReturn(responseProductPageDto);

        mockMvc.perform(get("/products/main"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("data").exists())
                .andExpect(jsonPath("data").isArray())
                .andExpect(jsonPath("data.length()").value(20));
    }

    @Test
    @DisplayName("조건 검색 테스트")
    void getSearchResultTest() throws Exception {
        ResponseProductPageDto responseProductPageDto = ResponseProductPageDto.builder()
                .data(responseProductDtos)
                .lastProductId(1L)
                .build();
        when(productService.getSearchResult(anyList(), anyList(), any(), any()))
                .thenReturn(responseProductPageDto);

        mockMvc.perform(get("/products?maincategory=ACCESSORIES&subcategory=BACKPACKS&searchword=search"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("data").exists());
    }

    @Test
    @DisplayName("상품 상세 조회 테스트")
    void getDetailProductTest() throws Exception {
        ResponseProductDetailDto responseProductDetailDto = ResponseProductDetailDto.builder().build();
        when(productService.getDetailProduct(any()))
                .thenReturn(responseProductDetailDto);

        mockMvc.perform(get("/products/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("data").exists());
    }

}