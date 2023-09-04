package com.maybezone.productservice.domain.product.repository;

import com.maybezone.productservice.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
