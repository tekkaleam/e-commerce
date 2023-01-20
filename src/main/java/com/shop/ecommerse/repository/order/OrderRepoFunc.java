package com.shop.ecommerse.repository.order;

import com.shop.ecommerse.domain.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepoFunc {

    Order save(Order order, Long userId);
    List<Order> findAllForUserId(Long userId);
    Order findById(Long orderId, Long userId);
    int delete(Long orderId, Long userId);

    Optional<Order> findWithUser(Long id, Long userId);

    Optional<Order> findOrderByPhoneNumber(String phoneNumber);
}
