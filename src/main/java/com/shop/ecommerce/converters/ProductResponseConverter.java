package com.shop.ecommerce.converters;

import com.shop.ecommerce.domain.dto.ProductVariantDTO;
import com.shop.ecommerce.domain.entity.Product;
import com.shop.ecommerce.domain.response.product.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductResponseConverter implements Function<Product, ProductResponse> {

    @Override
    public ProductResponse apply(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setName(product.getName());
        productResponse.setUrl(product.getUrl());
        productResponse.setProductVariants(product.getProductVariantList()
                .stream()
                .map(variant -> ProductVariantDTO
                        .builder()
                        .id(variant.getId())
                        .price(variant.getPrice())
                        .stock(variant.getStock())
                        .build())
                .collect(Collectors.toList()));

        return productResponse;
    }
}
