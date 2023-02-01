package com.shop.ecommerse.converters;

import com.shop.ecommerse.domain.entity.ProductCategory;
import com.shop.ecommerse.domain.response.product.ProductCategoryResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProductCategoryResponseConverter implements Function<ProductCategory, ProductCategoryResponse> {
    @Override
    public ProductCategoryResponse apply(ProductCategory productCategory) {
        return ProductCategoryResponse.builder().name(productCategory.getName()).build();
    }
}
