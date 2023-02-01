package com.shop.ecommerse.domain.response.product;

import com.shop.ecommerse.domain.dto.ProductVariantDTO;
import lombok.Data;

import java.util.List;
// TODO to record
@Data
public class ProductResponse {
    private String name;
    private String url;
    private List<ProductVariantDTO> productVariants;
}

