package com.shop.ecommerce.domain.dto;

import lombok.Builder;
import lombok.Data;

// TODO to record
@Data
@Builder
public class OrderDetailDTO {
    private String url;

    private String name;

    private Float price;

    private Float cargoPrice;

 //   private String thumb;

    private Integer amount;

    private CategoryDTO category;

  //  private ColorDTO color;
}
