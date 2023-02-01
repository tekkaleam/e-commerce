package com.shop.ecommerse.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

// TODO to record

@Data
@AllArgsConstructor
@Builder
public class ProductVariantDTO {
    private Long id;
    private Float price;
    private String thumb;
    private Integer stock;
}
