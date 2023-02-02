package com.shop.ecommerce.domain.request.discount;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ApplyDiscountRequest {

    @NotBlank
    private String code;
}
