package com.shop.ecommerse.service.cache;

import com.shop.ecommerse.domain.entity.ProductVariant;
import com.shop.ecommerse.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "product_variant")
public class ProductVariantCacheService {
    private final ProductVariantRepository productVariantRepository;

    @Autowired
    public ProductVariantCacheService(ProductVariantRepository productVariantRepository) {
        this.productVariantRepository = productVariantRepository;
    }

    @Cacheable(key = "{#root.methodName,#id}")
    public ProductVariant findById(Long id) {
        return productVariantRepository.findById(id).orElse(null);
    }

    @Cacheable(key = "#root.methodName", unless = "#result.size()==0")
    public List<ProductVariant> findTop8ByOrderBySellCountDesc() {
        return productVariantRepository.findTop8ByOrderBySellCountDesc();
    }

}
