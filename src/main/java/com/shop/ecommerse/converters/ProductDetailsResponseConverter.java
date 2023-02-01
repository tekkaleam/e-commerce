package com.shop.ecommerse.converters;

import com.shop.ecommerse.domain.dto.CategoryDTO;
import com.shop.ecommerse.domain.dto.ProductVariantDetailDTO;
import com.shop.ecommerse.domain.entity.Product;
import com.shop.ecommerse.domain.response.product.ProductDetailsResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProductDetailsResponseConverter implements Function<Product, ProductDetailsResponse> {

    @Override
    public ProductDetailsResponse apply(Product product) {
        ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
        productDetailsResponse.setName(product.getName());
        productDetailsResponse.setUrl(product.getUrl());
        productDetailsResponse.setCategory(CategoryDTO.builder().name(product.getProductCategory().getName()).build());
        productDetailsResponse.setProductVariantDetails(
                product.getProductVariantList()
                        .stream()
                        .map(productVariant -> ProductVariantDetailDTO
                                .builder()
                                .id(productVariant.getId())
                                .price(productVariant.getPrice())
                                .image(productVariant.getImage())
                                .stock(productVariant.getStock())
                                .live(productVariant.getLive())
                                .build())
                        .collect(Collectors.toList())
        );

        return productDetailsResponse;
    }
}