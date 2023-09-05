package com.maybezone.productservice.domain.product.repository;

import com.maybezone.productservice.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

import static jakarta.persistence.LockModeType.*;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Lock(PESSIMISTIC_READ)
    Optional<Product> findById(Long id);

}
