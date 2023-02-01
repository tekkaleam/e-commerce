package com.shop.ecommerse.service;

import com.shop.ecommerse.domain.entity.Cart;
import com.shop.ecommerse.domain.entity.Discount;
import com.shop.ecommerse.handler.exceptions.InvalidArgumentException;
import com.shop.ecommerse.handler.exceptions.ResourceNotFoundException;
import com.shop.ecommerse.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl implements DiscountService{

    private final DiscountRepository discountRepository;
    private final CartService cartService;

    @Autowired
    public DiscountServiceImpl(DiscountRepository discountRepository, CartService cartService) {
        this.discountRepository = discountRepository;
        this.cartService = cartService;
    }

    @Override
    public void applyDiscount(String code) {
        Discount discount = discountRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Discount code not found"));

        if (discount.getStatus() != 1) {
            throw new InvalidArgumentException("Discount code is expired!");
        }

        Cart cart = cartService.getCart();

        cart.setDiscount(discount);
        cart = cartService.calculatePrice(cart);
        cartService.saveCart(cart);
    }
}
