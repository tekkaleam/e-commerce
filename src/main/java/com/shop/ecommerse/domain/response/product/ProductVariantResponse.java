package com.shop.ecommerse.domain.response.product;

import com.shop.ecommerse.domain.dto.ProductVariantDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductVariantResponse {

    private Long id;
    private String name;
    private String url;
    private ProductVariantDTO productVariant;
}
