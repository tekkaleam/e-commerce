package com.shop.ecommerce.domain.request.cart;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RemoveItemFromCartRequest {

    @NotNull
    @Min(1)
    private Long cartItemId;

}
