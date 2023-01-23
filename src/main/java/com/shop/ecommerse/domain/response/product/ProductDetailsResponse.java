package com.shop.ecommerse.domain.response.product;

import com.shop.ecommerse.domain.dto.CategoryDTO;
import com.shop.ecommerse.domain.dto.ProductVariantDetailDTO;
import lombok.Data;

import java.util.List;

@Data
public class ProductDetailsResponse {
    private String name;
    private String url;
    private String sku;
    private String longDesc;
    private CategoryDTO category;
    private List<ProductVariantDetailDTO> productVariantDetails;
}