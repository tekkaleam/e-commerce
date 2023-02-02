package com.shop.ecommerce.service;

import com.shop.ecommerce.domain.request.order.PostOrderRequest;
import com.shop.ecommerce.domain.response.order.OrderResponse;

import java.util.List;

public interface OrderService {

    Integer getAllOrdersCount();

    List<OrderResponse> getAllOrders(Integer page, Integer pageSize);

    OrderResponse postOrder(PostOrderRequest postOrderRequest);

}
