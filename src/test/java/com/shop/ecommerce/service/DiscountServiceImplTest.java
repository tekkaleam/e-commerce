package com.shop.ecommerce.service;

import com.github.javafaker.Faker;
import com.shop.ecommerce.domain.entity.Cart;
import com.shop.ecommerce.domain.entity.Discount;
import com.shop.ecommerce.handler.exceptions.InvalidArgumentException;
import com.shop.ecommerce.handler.exceptions.ResourceNotFoundException;
import com.shop.ecommerce.repository.DiscountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DiscountServiceImplTest {

    @Mock
    private DiscountRepository discountRepository;
    @Mock
    private CartService cartService;
    @InjectMocks
    private DiscountServiceImpl underTest;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }
    
    @Test
    void isShouldApplyDiscount() {
        // given
        String code = faker.lorem().word();
        Discount discount = new Discount();
        discount.setStatus(1);
        Cart cart = new Cart();

        given(discountRepository.findByCode(code)).willReturn(Optional.of(discount));
        given(cartService.getCart()).willReturn(cart);
        given(cartService.calculatePrice(any(Cart.class))).willReturn(cart);

        // when
        underTest.applyDiscount(code);
        // then
        verify(discountRepository).findByCode(code);
        verify(cartService).getCart();
        verify(cartService).saveCart(cart);
    }

    @Test
    void isShouldThrowExceptionIfDiscountNotFound() {
        // given
        String code = faker.lorem().word();

        given(discountRepository.findByCode(code)).willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> underTest.applyDiscount(code))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Discount code not found");
    }

    @Test
    void isShouldThrowExceptionIfDiscountStatusIsNot1() {
        String code = faker.lorem().word();
        Discount discount = new Discount();
        discount.setStatus(0);

        given(discountRepository.findByCode(code)).willReturn(Optional.of(discount));

        assertThatThrownBy(() -> underTest.applyDiscount(code))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessage("Discount code is expired!");
    }
}