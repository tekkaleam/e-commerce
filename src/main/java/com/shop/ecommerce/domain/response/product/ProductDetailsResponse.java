package com.shop.ecommerce.domain.response.product;

import com.shop.ecommerce.domain.dto.CategoryDTO;
import com.shop.ecommerce.domain.dto.ProductVariantDetailDTO;
import lombok.Data;

import java.util.List;

    // TODO to record
@Data
public class ProductDetailsResponse {
    private String name;
    private String url;
    private String sku;
    private String longDesc;
    private CategoryDTO category;
    private List<ProductVariantDetailDTO> productVariantDetails;
}