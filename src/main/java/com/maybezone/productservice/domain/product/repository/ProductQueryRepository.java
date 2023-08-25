package com.maybezone.productservice.domain.product.repository;

import com.maybezone.productservice.domain.product.entity.Product;
import com.maybezone.productservice.domain.product.productenum.MainCategory;
import com.maybezone.productservice.domain.product.productenum.SubCategory;
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

    public Page<Product> searchProducts(
            List<MainCategory> mainCategories,
            List<SubCategory> subCategories,
            String searchWord,
            Pageable pageable
    ) {
        List<Product> products = queryFactory
                .selectFrom(product)
                .where(
                        equalMainCategory(mainCategories),
                        equalSubCategory(subCategories),
                        equalSearchWord(searchWord)
                )
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(product.count())
                .from(product)
                .where(
                        equalMainCategory(mainCategories),
                        equalSubCategory(subCategories),
                        equalSearchWord(searchWord)
                );

        return PageableExecutionUtils.getPage(products, pageable, countQuery::fetchOne);

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

        return product.name.like("%" + searchWord + "%");
    }

}
