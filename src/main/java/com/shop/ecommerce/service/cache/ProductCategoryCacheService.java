package com.shop.ecommerce.service.cache;

import com.shop.ecommerce.domain.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryCacheService {
    List<ProductCategory> findAllByOrderByName();
}
