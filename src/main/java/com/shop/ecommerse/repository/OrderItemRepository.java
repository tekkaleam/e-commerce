package com.shop.ecommerse.repository;

import com.shop.ecommerse.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
