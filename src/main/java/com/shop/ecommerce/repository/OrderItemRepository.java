package com.shop.ecommerce.repository;

import com.shop.ecommerce.domain.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderDetail, Long> {

}
