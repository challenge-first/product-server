package com.maybezone.productservice.domain.product.entity;

import com.maybezone.productservice.domain.product.productenum.MainCategory;
import com.maybezone.productservice.domain.product.productenum.ProductState;
import com.maybezone.productservice.domain.product.productenum.ProductType;
import com.maybezone.productservice.domain.product.productenum.SubCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "products")
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Long stockCount;

    @Column(nullable = false)
    private Long likeCount;

    @Enumerated(STRING)
    @Column(nullable = false)
    private ProductState productState;

    @Enumerated(STRING)
    @Column(nullable = false)
    private ProductType productType;

    @Enumerated(STRING)
    @Column(nullable = false)
    private MainCategory mainCategory;

    @Enumerated(STRING)
    @Column(nullable = false)
    private SubCategory subCategory;

    public Long updateStockCount() {

        if (stockCount <= 0) {
            throw new IllegalStateException("상품의 재고가 부족합니다");
        }

        return --this.stockCount;
    }
}
