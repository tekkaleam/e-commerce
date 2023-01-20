package com.shop.ecommerse.repository;

import com.shop.ecommerse.domain.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<ProductCategory, Integer> {

}
