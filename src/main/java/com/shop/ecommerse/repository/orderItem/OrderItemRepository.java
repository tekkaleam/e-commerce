package com.shop.ecommerse.repository.orderItem;

import com.shop.ecommerse.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
