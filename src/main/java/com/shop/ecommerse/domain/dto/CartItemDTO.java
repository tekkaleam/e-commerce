package com.shop.ecommerse.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class CartItemDTO{

    private Long id;
    private String url;
    private String name;
    private Float price;
    private Integer amount;
    private String thumb;
    private Integer stock;
}
