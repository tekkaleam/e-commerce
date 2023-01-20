package com.shop.ecommerse.repository;

import com.shop.ecommerse.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShopItemRepository extends JpaRepository<Product, Long> {

}
