package com.shop.ecommerce.domain.response.product;

import com.shop.ecommerce.domain.dto.ProductVariantDTO;
import lombok.Data;

import java.util.List;
// TODO to record
@Data
public class ProductResponse {
    private String name;
    private String url;
    private List<ProductVariantDTO> productVariants;
}

