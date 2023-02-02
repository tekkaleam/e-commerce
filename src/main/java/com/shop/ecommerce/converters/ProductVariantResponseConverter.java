package com.shop.ecommerce.converters;

import com.shop.ecommerce.domain.dto.ProductVariantDTO;
import com.shop.ecommerce.domain.entity.ProductVariant;
import com.shop.ecommerce.domain.response.product.ProductVariantResponse;
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
