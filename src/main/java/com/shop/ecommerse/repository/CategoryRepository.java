package com.shop.ecommerse.repository;

import com.shop.ecommerse.domain.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<ProductCategory, Integer> {
    List<ProductCategory> findAllByOrderByName();
}
