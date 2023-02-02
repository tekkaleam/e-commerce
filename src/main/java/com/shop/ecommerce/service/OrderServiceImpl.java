package com.shop.ecommerce.service;


import com.shop.ecommerce.converters.OrderResponseConverter;
import com.shop.ecommerce.domain.entity.Cart;
import com.shop.ecommerce.domain.entity.Order;
import com.shop.ecommerce.domain.entity.OrderDetail;
import com.shop.ecommerce.domain.entity.User;
import com.shop.ecommerce.domain.request.order.PostOrderRequest;
import com.shop.ecommerce.domain.response.order.OrderResponse;
import com.shop.ecommerce.handler.exceptions.InvalidArgumentException;
import com.shop.ecommerce.handler.exceptions.ResourceFetchException;
import com.shop.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService  {

    private final OrderResponseConverter converter;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public OrderServiceImpl(OrderResponseConverter converter, OrderRepository orderRepository, UserService userService, CartService cartService) {
        this.converter = converter;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
    }


    @Override
    public Integer getAllOrdersCount() {
        User user = userService.getUser();
        return orderRepository.countAllByUser(user)
                .orElseThrow(() -> new ResourceFetchException("An error occurred whilst fetching orders count"));
    }

    @Override
    public List<OrderResponse> getAllOrders(Integer page, Integer pageSize) {
        User user = userService.getUser();
        List<Order> orders = orderRepository.findAllByUserOrderByCreationDateDesc(user, PageRequest.of(page, pageSize));
        return orders
                .stream()
                .map(converter)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse postOrder(PostOrderRequest postOrderRequest) {
        User user = userService.getUser();
        Cart cart = user.getCart();
        if (Objects.isNull(cart) || Objects.isNull(cart.getCartItemList())) {
            throw new InvalidArgumentException("Cart is not valid");
        }

        if (cart.getCartItemList().stream().anyMatch(cartItem -> cartItem.getProductVariant().getStock() < cartItem.getAmount())) {
            throw new InvalidArgumentException("A product in your cart is out of stock.");
        }

        Order saveOrder = new Order();
        saveOrder.setUser(user);
        saveOrder.setPhone(postOrderRequest.getPhone());
        saveOrder.setShipAddress(postOrderRequest.getShipAddress());
        saveOrder.setCity(postOrderRequest.getCity());
        saveOrder.setCountry(postOrderRequest.getCountry());

        saveOrder.setOrderDetails(new ArrayList<>());

        cart.getCartItemList().forEach(cartItem -> {
            cartItem.getProductVariant().setSellCount(cartItem.getProductVariant().getSellCount() + cartItem.getAmount());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setAmount(cartItem.getAmount());
            orderDetail.setOrder(saveOrder);
            orderDetail.setProductVariant(cartItem.getProductVariant());
            saveOrder.getOrderDetails().add(orderDetail);
        });

        saveOrder.setTotalPrice(cart.getTotalPrice());
        saveOrder.setDiscount(cart.getDiscount());
        saveOrder.setShipped(0);


        Order order = orderRepository.save(saveOrder);
        cartService.emptyCart();
        return converter.apply(order);
    }
}
