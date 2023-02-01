package com.shop.ecommerse.domain.request.cart;

import com.shop.ecommerse.domain.dto.CartItemDTO;
import com.shop.ecommerse.domain.dto.DiscountDTO;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class ConfirmCartRequest {

    @NotNull
    private List<CartItemDTO> cartItems;

    private DiscountDTO discount;

    @Min(0)
    private Float totalCartPrice;

    @Min(0)
    private Float totalCargoPrice;

    @Min(0)
    private Float totalPrice;
}
