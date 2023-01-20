package com.shop.ecommerse.repository.order;

import com.shop.ecommerse.domain.entity.Order;
import com.shop.ecommerse.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepoFunc{
    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    @Autowired
    public OrderRepositoryImpl(@Lazy OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Order save(Order order, Long userId) {
        if(!order.isNew() && findById(order.getId(), userId) == null) {
            return null;
        }
        order.setUser(userRepository.getReferenceById(userId));
        return orderRepository.save(order);
    }

    @Override
    public Order findById(Long id, Long userId) {
        return orderRepository.findById(id)
                .filter(o -> Objects.requireNonNull(o.getUser().getId()).equals(userId))
                .orElse(null);
    }

    @Override
    public List<Order> findAllForUserId(Long userId) {
        return orderRepository.findAll(userId);
    }

    @Override
    public int delete(Long orderId, Long userId) {
        return orderRepository.delete(orderId, userId);
    }

    @Override
    public Optional<Order> findWithUser(Long id, Long userId) {
        return orderRepository.findWithUser(id, userId);
    }

    @Override
    public Optional<Order> findOrderByPhoneNumber(String phoneNumber) {
        return orderRepository.findByPhone(phoneNumber);
    }


}
