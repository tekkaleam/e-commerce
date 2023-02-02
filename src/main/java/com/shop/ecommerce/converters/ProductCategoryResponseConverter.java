package com.shop.ecommerce.converters;

import com.shop.ecommerce.domain.entity.ProductCategory;
import com.shop.ecommerce.domain.response.product.ProductCategoryResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProductCategoryResponseConverter implements Function<ProductCategory, ProductCategoryResponse> {
    @Override
    public ProductCategoryResponse apply(ProductCategory productCategory) {
        return ProductCategoryResponse.builder().name(productCategory.getName()).build();
    }
}
