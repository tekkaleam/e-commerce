package com.shop.ecommerse.domain.response.product;

import com.shop.ecommerse.domain.dto.ProductVariantDTO;
import lombok.Data;

@Data
public class ProductVariantResponse {

    private Long id;
    private String name;
    private String url;
    private ProductVariantDTO productVariant;
}
