package com.shop.ecommerce.repository;

import com.shop.ecommerce.domain.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<ProductCategory, Integer> {
    List<ProductCategory> findAllByOrderByName();
}
