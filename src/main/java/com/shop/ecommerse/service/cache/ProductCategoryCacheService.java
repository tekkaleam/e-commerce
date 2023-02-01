package com.shop.ecommerse.service.cache;

import com.shop.ecommerse.domain.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryCacheService {
    List<ProductCategory> findAllByOrderByName();
}
