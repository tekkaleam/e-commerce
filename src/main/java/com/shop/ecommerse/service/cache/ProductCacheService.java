package com.shop.ecommerse.service.cache;

import com.shop.ecommerse.domain.entity.Product;
import com.shop.ecommerse.domain.entity.ProductCategory;

import java.util.List;

public interface ProductCacheService {
    Product findByUrl(String url);

    List<Product> findTop8ByOrderByDateCreatedDesc();

    List<Product> getRelatedProducts(ProductCategory productCategory, Long id);
}
