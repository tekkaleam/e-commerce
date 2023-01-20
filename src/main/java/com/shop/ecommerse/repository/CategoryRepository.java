package com.shop.ecommerse.repository;

import com.shop.ecommerse.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
