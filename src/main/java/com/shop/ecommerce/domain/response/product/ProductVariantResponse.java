package com.shop.ecommerce.domain.response.product;

import com.shop.ecommerce.domain.dto.ProductVariantDTO;
import lombok.Builder;
import lombok.Data;

// TODO to record
@Data
@Builder
public class ProductVariantResponse {

    private Long id;
    private String name;
    private String url;
    private ProductVariantDTO productVariant;
}
