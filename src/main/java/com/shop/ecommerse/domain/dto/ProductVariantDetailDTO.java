package com.shop.ecommerse.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductVariantDetailDTO {
    private Long id;
    private Float price;
    private String image;
    private String thumb;
    private Integer stock;
    private Integer live;
}
