package com.shop.ecommerse.repository.product;


import com.shop.ecommerse.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryFunc {

    Optional<Product> findById(Long id);
    List<Product> findAll();
    int delete(Long id);
    Optional<Product> findByProductName(String name);
    List<Product> findProductsBetweenPrice(Integer bottomPrice, Integer roofPrice);
    Product save(Product product);
 }
