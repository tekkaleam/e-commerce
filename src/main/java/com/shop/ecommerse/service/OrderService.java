package com.shop.ecommerse.service;

import com.shop.ecommerse.domain.request.order.PostOrderRequest;
import com.shop.ecommerse.domain.response.order.OrderResponse;

import java.util.List;

public interface OrderService {

    Integer getAllOrdersCount();

    List<OrderResponse> getAllOrders(Integer page, Integer pageSize);

    OrderResponse postOrder(PostOrderRequest postOrderRequest);

}
