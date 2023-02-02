package com.shop.ecommerce.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiscountDTO{

    Integer discountPercent;
    Integer status;
}
