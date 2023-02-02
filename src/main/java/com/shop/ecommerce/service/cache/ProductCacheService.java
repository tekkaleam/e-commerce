package com.shop.ecommerce.service.cache;

import com.shop.ecommerce.domain.entity.Product;
import com.shop.ecommerce.domain.entity.ProductCategory;

import java.util.List;

public interface ProductCacheService {
    Product findByUrl(String url);

    List<Product> findTop8ByOrderByDateCreatedDesc();

    List<Product> getRelatedProducts(ProductCategory productCategory, Long id);
}
