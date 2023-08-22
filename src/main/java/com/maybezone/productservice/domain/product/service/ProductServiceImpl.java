package com.maybezone.productservice.domain.product.service;

import com.maybezone.productservice.domain.product.dto.response.ResponseProductDetailDto;
import com.maybezone.productservice.domain.product.dto.response.ResponseProductDto;
import com.maybezone.productservice.domain.product.entity.Product;
import com.maybezone.productservice.domain.product.productenum.MainCategory;
import com.maybezone.productservice.domain.product.productenum.SubCategory;
import com.maybezone.productservice.domain.product.repository.ProductQueryRepository;
import com.maybezone.productservice.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.maybezone.productservice.domain.product.mapper.ProductMapper.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepository;

    @Override
    public List<ResponseProductDto> getMainPageProducts(Pageable pageable) {
        List<Product> findProducts = productRepository.findTop4ByOrderByIdDesc(pageable);

        return findProducts.stream()
                .map(PRODUCT_INSTANCE::entityToResponseProductDto)
                .toList();
    }

    @Override
    public List<ResponseProductDto> getSearchResult(List<String> mainCategories, List<String> subCategories, String searchWord, Pageable pageable) {
        List<MainCategory> enumMainCategories = new ArrayList<>();
        List<SubCategory> enumSubCategories = new ArrayList<>();

        if (isNotEmptyList(mainCategories)) {
            mainCategories.forEach(mainCategory -> {
                MainCategory enumMainCategory = getEnumMainCategory(mainCategory);
                enumMainCategories.add(enumMainCategory);
            });
        }

        if (isNotEmptyList(subCategories)) {
            subCategories.forEach(subCategory -> {
                SubCategory enumSubCategory = getEnumSubCategory(subCategory);
                enumSubCategories.add(enumSubCategory);
            });
        }

        List<Product> findProducts = productQueryRepository.searchProducts(enumMainCategories, enumSubCategories, searchWord, pageable);

        return findProducts.stream()
                .map(PRODUCT_INSTANCE::entityToResponseProductDto)
                .toList();
    }

    private static boolean isNotEmptyList(List<String> mainCategories) {
        return !mainCategories.isEmpty();
    }

    private MainCategory getEnumMainCategory(String mainCategory) {
        try {
            return MainCategory.valueOf(mainCategory);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("해당 메인 카테고리는 없는 카테고리입니다.");
        }
    }

    private SubCategory getEnumSubCategory(String subCategory) {
        try {
            return SubCategory.valueOf(subCategory);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("해당 서브 카테고리는 없는 카테고리입니다.");
        }
    }

    @Override
    public ResponseProductDetailDto getDetailProduct(Long productId) {
        Product findProduct = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));

        return PRODUCT_INSTANCE.entityToResponseProductDetailDto(findProduct);
    }
}
