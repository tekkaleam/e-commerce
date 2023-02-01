package com.shop.ecommerse.repository;

import com.shop.ecommerse.domain.entity.Order;
import com.shop.ecommerse.domain.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT O FROM Order O WHERE O.id=:id AND O.user.id=:userId")
    Optional<Order> findByUserIdAndOrderId(@Param("id") Long id, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Order  O WHERE O.id=:id and O.user.id=:userId")
    int delete(@Param("id") Long id, @Param("userId") Long userId);

    @Query("select o from Order o where o.user.id=:userId")
    List<Order> findAll(@Param("userId") Long userId);

    @Query("select o from Order o join fetch o.user where o.id=?1 and o.user.id=?2")
    Optional<Order> findWithUser(Long id, Long userId);

    Optional<Order> findByPhone(String phone);

    List<Order> findAllByUserOrderByCreationDateDesc(User user, Pageable pageable);

    Optional<Integer> countAllByUser(User user);
}
