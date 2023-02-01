package com.shop.ecommerse.service;

import com.github.javafaker.Faker;
import com.shop.ecommerse.converters.OrderResponseConverter;
import com.shop.ecommerse.domain.entity.*;
import com.shop.ecommerse.domain.request.order.PostOrderRequest;
import com.shop.ecommerse.domain.response.order.OrderResponse;
import com.shop.ecommerse.handler.exceptions.InvalidArgumentException;
import com.shop.ecommerse.handler.exceptions.ResourceFetchException;
import com.shop.ecommerse.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderResponseConverter converter;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserService userService;
    @Mock
    private CartService cartService;

    @InjectMocks
    private OrderServiceImpl underTest;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    @Test
    void isShouldGetAllOrdersCount() {
        // given
        User user = new User();
        given(userService.getUser()).willReturn(user);
        given(orderRepository.countAllByUser(user)).willReturn(Optional.of(2));

        // then
        then(underTest.getAllOrdersCount())
                .isNotNull()
                .isEqualTo(2);
    }

    @Test
    void isShouldThrowExceptionIfGetAllCountReturnsNull() {

        User user = new User();
        given(userService.getUser()).willReturn(user);
        given(orderRepository.countAllByUser(user)).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getAllOrdersCount())
                .isInstanceOf(ResourceFetchException.class)
                .hasMessage("An error occurred whilst fetching orders count");
    }

    @Test
    void isShouldGetAllOrders() {
        // given
        User user = new User();
        int page = faker.number().numberBetween(1, 5);
        int pageSize = faker.number().numberBetween(1, 10);
        Order order = new Order();
        List<Order> orders = Collections.singletonList(order);
        OrderResponse response = OrderResponse.builder().build();
        List<OrderResponse> expected = Collections.singletonList(response);

        given(userService.getUser()).willReturn(user);
        given(orderRepository.findAllByUserOrderByCreationDateDesc(user, PageRequest.of(page, pageSize)))
                .willReturn(orders);
        given(converter.apply(order)).willReturn(response);

        // then
        then(underTest.getAllOrders(page, pageSize))
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expected);
    }

    @Test
    void isShouldPostOrder() {
        // given
        User user = new User();
        Cart cart = new Cart();

        ProductVariant variant = new ProductVariant();
        variant.setStock(faker.number().numberBetween(5, 10));
        variant.setSellCount(faker.number().randomDigitNotZero());

        CartItem item = new CartItem();
        item.setAmount(faker.number().numberBetween(1, 3));
        item.setProductVariant(variant);

        cart.setCartItemList(Collections.singletonList(item));
        user.setCart(cart);

        OrderResponse expected = OrderResponse.builder().build();
        Order order = new Order();

        given(userService.getUser()).willReturn(user);
        given(orderRepository.save(any(Order.class))).willReturn(order);
        given(converter.apply(any(Order.class))).willReturn(expected);

        // then
        then(underTest.postOrder(new PostOrderRequest()))
                .isNotNull()
                .isEqualTo(expected);
    }

    @Test
    void isShouldThrowExceptionIfCartIsNull() {

        given(userService.getUser()).willReturn(new User());

        assertThatThrownBy(() -> underTest.postOrder(new PostOrderRequest()))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessage("Cart is not valid");
    }

    @Test
    void isShouldThrowExceptionIdCartItemsInCartNull() {
        // given
        User user = new User();
        Cart cart = new Cart();
        user.setCart(cart);

        given(userService.getUser()).willReturn(new User());

        assertThatThrownBy(() -> underTest.postOrder(new PostOrderRequest()))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessage("Cart is not valid");
    }

    @Test
    void isShouldThrowExceptionIfInStockLessThenBuyingAmount() {
        // given
        User user = new User();
        Cart cart = new Cart();

        ProductVariant variant = new ProductVariant();
        variant.setStock(faker.number().numberBetween(1, 3));
        variant.setSellCount(faker.number().randomDigitNotZero());

        CartItem item = new CartItem();
        item.setAmount(faker.number().numberBetween(10, 30));
        item.setProductVariant(variant);

        cart.setCartItemList(Collections.singletonList(item));
        user.setCart(cart);

        given(userService.getUser()).willReturn(user);

        // then
        assertThatThrownBy(() -> underTest.postOrder(new PostOrderRequest()))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessage("A product in your cart is out of stock.");

    }
}