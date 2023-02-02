package com.shop.ecommerce.converters;

import com.shop.ecommerce.domain.dto.CategoryDTO;
import com.shop.ecommerce.domain.dto.ProductVariantDetailDTO;
import com.shop.ecommerce.domain.entity.Product;
import com.shop.ecommerce.domain.response.product.ProductDetailsResponse;
import org.springframework.stereotype.Component;

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