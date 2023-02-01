package com.shop.ecommerse.service;

import com.github.javafaker.Faker;
import com.shop.ecommerse.converters.CartResponseConverter;
import com.shop.ecommerse.domain.dto.CartItemDTO;
import com.shop.ecommerse.domain.entity.*;
import com.shop.ecommerse.domain.request.cart.ConfirmCartRequest;
import com.shop.ecommerse.domain.response.cart.CartResponse;
import com.shop.ecommerse.handler.exceptions.InvalidArgumentException;
import com.shop.ecommerse.handler.exceptions.ResourceNotFoundException;
import com.shop.ecommerse.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductService productService;
    @Mock
    private UserService userService;
    @Mock
    private CartResponseConverter cartResponseConverter;
    @InjectMocks
    private CartServiceImpl underTest;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    @Test
    void isShouldAddToCart() {
        // given
        User user = new User();
        Cart cart = new Cart();
        ProductVariant variant = new ProductVariant();
        CartResponse expected = new CartResponse();
        Long productVariantId = faker.number().randomNumber();
        Integer amount = faker.number().numberBetween(2, 4);

        variant.setId(productVariantId);
        variant.setPrice((float) faker.random().nextDouble());
        variant.setStock(faker.number().numberBetween(5, 10));
        variant.setSellCount(faker.number().randomDigitNotZero());

        CartItem item = new CartItem();
        item.setAmount(faker.number().numberBetween(1, 3));

        item.setProductVariant(variant);
        cart.setCartItemList(Collections.singletonList(item));
        user.setCart(cart);

        given(userService.getUser()).willReturn(user);
        given(cartRepository.save(any(Cart.class))).willReturn(cart);
        given(cartResponseConverter.apply(any(Cart.class))).willReturn(expected);

        CartResponse response = underTest.addToCart(productVariantId, amount);
        // then
        verify(cartRepository).save(any(Cart.class));
        verify(cartResponseConverter).apply(any(Cart.class));
        then(response).isNotNull().isEqualTo(expected);
    }

    @Test
    void isShouldProductDoesNotHaveDesiredStock() {
        User user = new User();
        Cart cart = new Cart();
        ProductVariant variant = new ProductVariant();
        Long productVariantId = faker.number().randomNumber();
        Integer amount = faker.number().numberBetween(10, 40);

        variant.setId(productVariantId);
        variant.setPrice((float) faker.random().nextDouble());
        variant.setStock(faker.number().numberBetween(5, 10));
        variant.setSellCount(faker.number().randomDigitNotZero());

        CartItem item = new CartItem();
        item.setAmount(faker.number().numberBetween(1, 3));

        item.setProductVariant(variant);
        cart.setCartItemList(Collections.singletonList(item));
        user.setCart(cart);

        given(userService.getUser()).willReturn(user);

        assertThatThrownBy(() -> underTest.addToCart(productVariantId, amount))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessage("Product does not have desired stock.");
    }

    @Test
    void isShouldIncrementCartItem() {
        User user = new User();
        Cart cart = new Cart();
        ProductVariant variant = new ProductVariant();
        Long productVariantId = faker.number().randomNumber();
        Long cartItemId = (long) faker.number().randomDigitNotZero();
        Integer amount = faker.number().numberBetween(1, 4);
        CartResponse expected = new CartResponse();

        variant.setId(productVariantId);
        variant.setPrice((float) faker.random().nextDouble());
        variant.setStock(faker.number().numberBetween(5, 10));
        variant.setSellCount(faker.number().randomDigitNotZero());

        CartItem item = new CartItem();
        item.setId(cartItemId);
        item.setAmount(faker.number().numberBetween(1, 3));

        item.setProductVariant(variant);
        cart.setCartItemList(Collections.singletonList(item));
        user.setCart(cart);

        given(userService.getUser()).willReturn(user);
        given(cartRepository.save(any(Cart.class))).willReturn(cart);
        given(cartResponseConverter.apply(any(Cart.class))).willReturn(expected);

        // when
        CartResponse response = underTest.incrementCartItem(cartItemId, amount);

        // then
        verify(cartRepository).save(any(Cart.class));
        verify(cartResponseConverter).apply(any(Cart.class));
        then(response)
                .isNotNull()
                .isEqualTo(expected);
    }

    @Test
    void isShouldThrowExceptionIncrementingIfEmptyCart() {
        User user = new User();
        Long productVariantId = faker.number().randomNumber();
        Long cartItemId = (long) faker.number().randomDigitNotZero();
        Integer amount = faker.number().numberBetween(1, 4);

        given(userService.getUser()).willReturn(user);

        assertThatThrownBy(() -> underTest.incrementCartItem(cartItemId, amount))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Empty cart");

    }

    @Test
    void isShouldShouldThrowExceptionIncrementingIfNotFoundIdOfItemInTheCart() {
        // given
        User user = new User();
        Cart cart = new Cart();
        ProductVariant variant = new ProductVariant();
        Long productVariantId = faker.number().randomNumber();
        Long cartItemId = (long) faker.number().randomDigitNotZero();
        Integer amount = faker.number().numberBetween(1, 2);
        CartResponse expected = new CartResponse();

        variant.setId(productVariantId);
        variant.setPrice((float) faker.random().nextDouble());
        variant.setStock(faker.number().numberBetween(5, 10));
        variant.setSellCount(faker.number().randomDigitNotZero());

        CartItem item = new CartItem();
        item.setId(faker.number().randomNumber());
        item.setAmount(faker.number().numberBetween(5, 7));

        item.setProductVariant(variant);
        cart.setCartItemList(Collections.singletonList(item));
        user.setCart(cart);

        given(userService.getUser()).willReturn(user);

        // then
        assertThatThrownBy(() -> underTest.incrementCartItem(cartItemId, amount))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("CartItem not found");

    }

    @Test
    void isShouldDecrementCartItem() {
        User user = new User();
        Cart cart = new Cart();
        ProductVariant variant = new ProductVariant();
        Long productVariantId = faker.number().randomNumber();
        Long cartItemId = (long) faker.number().randomDigitNotZero();
        Integer amount = faker.number().numberBetween(1, 2);
        CartResponse expected = new CartResponse();

        variant.setId(productVariantId);
        variant.setPrice((float) faker.random().nextDouble());
        variant.setStock(faker.number().numberBetween(5, 10));
        variant.setSellCount(faker.number().randomDigitNotZero());

        CartItem item = new CartItem();
        item.setId(cartItemId);
        item.setAmount(faker.number().numberBetween(5, 7));

        item.setProductVariant(variant);
        cart.setCartItemList(Collections.singletonList(item));
        user.setCart(cart);

        given(userService.getUser()).willReturn(user);
        given(cartRepository.save(any(Cart.class))).willReturn(cart);
        given(cartResponseConverter.apply(any(Cart.class))).willReturn(expected);

        CartResponse response = underTest.decrementCartItem(cartItemId, amount);

        // then
        verify(cartRepository).save(any(Cart.class));
        verify(cartResponseConverter).apply(any(Cart.class));
        then(response)
                .isNotNull()
                .isEqualTo(expected);
    }

    @Test
    void isShouldThrowExceptionDecrementingIfEmptyCart() {

        User user = new User();
        Long cartItemId = (long) faker.number().randomDigitNotZero();
        Integer amount = faker.number().numberBetween(1, 4);

        given(userService.getUser()).willReturn(user);

        assertThatThrownBy(() -> underTest.decrementCartItem(cartItemId, amount))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Empty cart");
    }

    @Test
    void isShouldShouldThrowExceptionDecrementingIfNotFoundIdOfItemInTheCart() {
        // given
        User user = new User();
        Cart cart = new Cart();
        ProductVariant variant = new ProductVariant();
        Long productVariantId = faker.number().randomNumber();
        Long cartItemId = (long) faker.number().randomDigitNotZero();
        Integer amount = faker.number().numberBetween(1, 2);


        variant.setId(productVariantId);
        variant.setPrice((float) faker.random().nextDouble());
        variant.setStock(faker.number().numberBetween(5, 10));
        variant.setSellCount(faker.number().randomDigitNotZero());

        CartItem item = new CartItem();
        item.setId(faker.number().randomNumber());
        item.setAmount(faker.number().numberBetween(5, 7));

        item.setProductVariant(variant);
        cart.setCartItemList(Collections.singletonList(item));
        user.setCart(cart);

        given(userService.getUser()).willReturn(user);

        // then
        assertThatThrownBy(() -> underTest.decrementCartItem(cartItemId, amount))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("CartItem not found");
    }

    @Test
    void isShouldFetchCart() {
        // given
        User user = new User();
        Cart cart = new Cart();
        user.setCart(cart);
        CartResponse expected = new CartResponse();

        given(userService.getUser()).willReturn(user);
        given(cartResponseConverter.apply(any(Cart.class))).willReturn(expected);

        CartResponse response = underTest.fetchCart();
        // then
        verify(userService).getUser();
        verify(cartResponseConverter).apply(any(Cart.class));
        then(response).isEqualTo(expected);
    }

    @Test
    void isShouldFetchCartAndReturnNullWhenCartIsNull() {

        User user = new User();
        given(userService.getUser()).willReturn(user);
        // then
        then(underTest.fetchCart()).isNull();
    }

    @Test
    void isShouldRemoveFromCart() {
        // given
        User user = new User();
        Cart cart = new Cart();
        ProductVariant variant = new ProductVariant();
        Long productVariantId = faker.number().randomNumber();
        Long cartItemId = (long) faker.number().randomDigitNotZero();

        variant.setId(productVariantId);
        variant.setPrice((float) faker.random().nextDouble());
        variant.setStock(faker.number().numberBetween(5, 10));
        variant.setSellCount(faker.number().randomDigitNotZero());

        CartItem item = new CartItem();
        item.setId(cartItemId);
        item.setAmount(faker.number().numberBetween(5, 7));

        item.setProductVariant(variant);
        cart.setCartItemList(new ArrayList<>());
        cart.getCartItemList().add(item);
        user.setCart(cart);

        given(userService.getUser()).willReturn(user);

        // when
        CartResponse response = underTest.removeFromCart(cartItemId);

        // then
        verify(userService).getUser();
        then(response).isNull();
    }

    @Test
    void isShouldThrowExceptionRemovingIfCartItemNotFound() {
        // given
        User user = new User();
        Cart cart = new Cart();
        ProductVariant variant = new ProductVariant();
        Long productVariantId = faker.number().randomNumber();
        Long cartItemId = (long) faker.number().randomDigitNotZero();

        variant.setId(productVariantId);
        variant.setPrice((float) faker.random().nextDouble());
        variant.setStock(faker.number().numberBetween(5, 10));
        variant.setSellCount(faker.number().randomDigitNotZero());

        CartItem item = new CartItem();
        item.setId(faker.number().randomNumber());
        item.setAmount(faker.number().numberBetween(5, 7));

        item.setProductVariant(variant);
        cart.setCartItemList(new ArrayList<>());
        cart.getCartItemList().add(item);
        user.setCart(cart);

        given(userService.getUser()).willReturn(user);

        // then
        assertThatThrownBy(() -> underTest.removeFromCart(cartItemId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("CartItem not found");
    }

    @Test
    void isShouldThrowExceptionRemovingIfCartNullOrCartItemNotFound() {

        given(userService.getUser()).willReturn(new User());

        assertThatThrownBy(() -> underTest.removeFromCart(faker.number().randomNumber()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Cart or CartItem not found");

        User user = new User();
        Cart cart = new Cart();
        user.setCart(cart);

        given(userService.getUser()).willReturn(user);

        assertThatThrownBy(() -> underTest.removeFromCart(faker.number().randomNumber()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Cart or CartItem not found");

        cart.setCartItemList(new ArrayList<>());

        assertThatThrownBy(() -> underTest.removeFromCart(faker.number().randomNumber()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Cart or CartItem not found");

        CartItem item  = new CartItem();
        item.setId(faker.number().randomNumber());
        cart.getCartItemList().add(item);

        assertThatThrownBy(() -> underTest.removeFromCart(faker.number().randomNumber()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("CartItem not found");
    }

    @Test
    void isShouldConfirmCart() {
        User user = new User();
        Cart cart = new Cart();

        given(userService.getUser()).willReturn(user);

        then(underTest.confirmCart(ConfirmCartRequest.builder().build())).isFalse();

        cart.setCartItemList(new ArrayList<>());
        CartItem item = new CartItem();
        ProductVariant variant = new ProductVariant();
        variant.setId(faker.number().randomNumber());
        item.setId(faker.number().randomNumber());
        item.setAmount(faker.number().numberBetween(1, 4));
        item.setProductVariant(variant);
        cart.getCartItemList().add(item);
        cart.setTotalPrice((float) faker.random().nextDouble());
        user.setCart(cart);

        ConfirmCartRequest request = ConfirmCartRequest.builder()
                        .cartItems(List.of(CartItemDTO.builder().amount(faker.number().numberBetween(1, 4)).build()))
                        .build();

        then(underTest.confirmCart(request)).isFalse();

    }

    @Test
    void isShouldEmptyCart() {
        // given
        User user = new User();
        Cart cart = new Cart();
        user.setCart(cart);

        given(userService.getUser()).willReturn(user);

        // when
        underTest.emptyCart();

        // then
        then(user.getCart()).isNull();
    }


    @Test
    void isShouldGetCart() {
        // given
        User user = new User();
        Cart cart = new Cart();
        user.setCart(cart);

        given(userService.getUser()).willReturn(user);

        // then
        then(underTest.getCart())
                .isEqualTo(cart);
    }

    @Test
    void isShouldSaveCart() {
        Cart cart = new Cart();

        underTest.saveCart(cart);

        verify(cartRepository).save(cart);
    }

    @Test
    void isShouldNotSaveCartIfCartIsNull() {
        assertThatThrownBy(() ->
                underTest.saveCart(null))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessage("Cart is null");
    }

    @Test
    void it_should_calculate_price() {

        // given
        DecimalFormat twoDForm = new DecimalFormat("#.##");

        Float totalCartPrice = (float) faker.number().randomNumber();
        Float totalCargoPrice = (float) faker.number().randomNumber();
        Float totalPrice = totalCartPrice + totalCargoPrice;

        Cart cart = new Cart();
        cart.setTotalCartPrice(totalCartPrice);
//        cart.setTotalCargoPrice(totalCargoPrice);
        cart.setTotalPrice(totalPrice);

        ProductVariant productVariant = new ProductVariant();
        productVariant.setPrice((float) faker.number().randomDigitNotZero());

        CartItem cartItem = new CartItem();
        cartItem.setAmount(faker.number().randomDigitNotZero());
        cartItem.setProductVariant(productVariant);

        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem);

        cart.setCartItemList(cartItemList);


        // when
        Cart cartResult = underTest.calculatePrice(cart);

        // then
        Float totalPriceExpected = Float.parseFloat(twoDForm.format((productVariant.getPrice()) * cartItem.getAmount()));
        then(cartResult.getTotalPrice()).isEqualTo(totalPriceExpected);
        then(cartResult.getTotalCartPrice()).isEqualTo(Float.parseFloat(twoDForm.format(productVariant.getPrice() * cartItem.getAmount())));

    }

    @Test
    void it_should_calculate_price_with_discount() {

        // given
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        twoDForm.setDecimalFormatSymbols(dfs);

        Float totalCartPrice = (float) faker.number().randomNumber();
        Float totalCargoPrice = (float) faker.number().randomNumber();
        Float totalPrice = totalCartPrice + totalCargoPrice;

        Cart cart = new Cart();
        cart.setTotalCartPrice(totalCartPrice);
//        cart.setTotalCargoPrice(totalCargoPrice);
        cart.setTotalPrice(totalPrice);
        Discount discount = new Discount();
        discount.setDiscountPercent(faker.number().numberBetween(1, 100));
        cart.setDiscount(discount);

        ProductVariant productVariant = new ProductVariant();
        productVariant.setPrice((float) faker.number().randomDigitNotZero());

        CartItem cartItem = new CartItem();
        cartItem.setAmount(faker.number().randomDigitNotZero());
        cartItem.setProductVariant(productVariant);

        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem);

        cart.setCartItemList(cartItemList);


        // when
        Cart cartResult = underTest.calculatePrice(cart);

        // then
        Float totalPriceExpected = Float.parseFloat(twoDForm.format((productVariant.getPrice()*cartItem.getAmount())));
        Float totalPriceDiscountExpected = Float.parseFloat(twoDForm.format(totalPriceExpected - totalPriceExpected * discount.getDiscountPercent() / 100));
        then(cartResult.getTotalPrice()).isEqualTo(totalPriceDiscountExpected);
        then(cartResult.getTotalCartPrice()).isEqualTo(Float.parseFloat(twoDForm.format(productVariant.getPrice() * cartItem.getAmount())));
    }

}