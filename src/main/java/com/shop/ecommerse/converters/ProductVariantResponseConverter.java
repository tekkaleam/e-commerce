package com.shop.ecommerse.converters;

import com.shop.ecommerse.domain.dto.ProductVariantDTO;
import com.shop.ecommerse.domain.entity.ProductVariant;
import com.shop.ecommerse.domain.response.product.ProductVariantResponse;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductVariantResponseConverter implements Function<ProductVariant, ProductVariantResponse> {


    @Override
    public ProductVariantResponse apply(ProductVariant productVariant) {

        return ProductVariantResponse.builder()
                        .id(productVariant.getId())
                        .name(productVariant.getProduct().getName())
                        .url(productVariant.getProduct().getUrl())
                        .productVariant(ProductVariantDTO
                .builder()
                .id(productVariant.getId())
                .price(productVariant.getPrice())
                .stock(productVariant.getStock())
                .build())
                        .build();
    }

}
