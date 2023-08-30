package com.maybezone.productservice.domain.product.repository;

import com.maybezone.productservice.domain.product.entity.Product;
import com.maybezone.productservice.domain.product.productenum.MainCategory;
import com.maybezone.productservice.domain.product.productenum.SubCategory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.maybezone.productservice.domain.product.entity.QProduct.*;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final Long PAGE_SIZE = 20L;

    public List<Product> findNoOffsetPageById(Long productId) {

        return queryFactory
                .selectFrom(product)
                .where(ltProductId(productId))
                .orderBy(product.id.desc())
                .limit(PAGE_SIZE)
                .fetch();
    }

    public List<Product> searchProducts(
            List<MainCategory> mainCategories,
            List<SubCategory> subCategories,
            String searchWord,
            Long productId
    ) {
        return queryFactory
                .selectFrom(product)
                .where(
                        equalMainCategory(mainCategories),
                        equalSubCategory(subCategories),
                        equalSearchWord(searchWord)
                )
                .where(ltProductId(productId))
                .orderBy(product.id.desc())
                .limit(PAGE_SIZE)
                .fetch();
    }

    private BooleanExpression ltProductId(Long productId) {
        if (productId == null) {
            return null;
        }

        return product.id.lt(productId);
    }

    private BooleanExpression equalMainCategory(List<MainCategory> mainCategories) {
        if (mainCategories == null || mainCategories.isEmpty()) {
            return null;
        }

        return product.mainCategory.in(mainCategories);
    }

    private BooleanExpression equalSubCategory(List<SubCategory> subCategories) {
        if (subCategories == null || subCategories.isEmpty()) {
            return null;
        }

        return product.subCategory.in(subCategories);
    }

    private BooleanExpression equalSearchWord(String searchWord) {
        if (searchWord == null || searchWord.isEmpty()) {
            return null;
        }

        return product.name.like(searchWord + "%");
    }

}
