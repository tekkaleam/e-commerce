package com.shop.ecommerce.converters;

import com.shop.ecommerce.domain.dto.CartItemDTO;
import com.shop.ecommerce.domain.dto.DiscountDTO;
import com.shop.ecommerce.domain.entity.Cart;
import com.shop.ecommerce.domain.response.cart.CartResponse;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CartResponseConverter implements Function<Cart, CartResponse> {

    @Override
    public CartResponse apply(Cart cart) {
        CartResponse cartResponse = new CartResponse();

        cartResponse.setCartItems(cart.getCartItemList()
                .stream()
                .map(cartItem -> CartItemDTO
                        .builder()
                        .id(cartItem.getId())
                        .url(cartItem.getProductVariant().getProduct().getUrl())
                        .name(cartItem.getProductVariant().getProduct().getName())
                        .price(cartItem.getProductVariant().getPrice())
                        .amount(cartItem.getAmount())
                        .stock(cartItem.getProductVariant().getStock())
                        .build())
                .collect(Collectors.toList()));

        if (Objects.nonNull(cart.getDiscount())) {
            cartResponse.setDiscount(DiscountDTO
                    .builder()
                    .discountPercent(cart.getDiscount().getDiscountPercent())
                    .status(cart.getDiscount().getStatus())
                    .build()
            );
        }

        cartResponse.setTotalPrice(cart.getTotalPrice());
        return cartResponse;
    }
}
