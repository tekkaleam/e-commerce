package com.shop.ecommerce.domain.response.cart;

import com.shop.ecommerce.domain.dto.CartItemDTO;
import com.shop.ecommerce.domain.dto.DiscountDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {
   private List<CartItemDTO> cartItems;
   private DiscountDTO discount;
   private Float totalCartPrice;
   private Float totalCargoPrice;
   private Float totalPrice;
   private DiscountDTO discountDTO;

}
