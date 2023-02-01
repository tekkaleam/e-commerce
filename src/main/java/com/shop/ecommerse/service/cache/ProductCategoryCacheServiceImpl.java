package com.shop.ecommerse.service.cache;

import com.shop.ecommerse.domain.entity.ProductCategory;
import com.shop.ecommerse.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "product_category")
public class ProductCategoryCacheServiceImpl implements ProductCategoryCacheService{

    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductCategoryCacheServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Cacheable(key = "#root.methodName", unless = "#result.size()==0")
    public List<ProductCategory> findAllByOrderByName() {
        return categoryRepository.findAllByOrderByName();
    }

}
