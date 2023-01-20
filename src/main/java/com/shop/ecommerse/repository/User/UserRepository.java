package com.shop.ecommerse.repository.User;

import com.shop.ecommerse.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT U FROM Users U WHERE U.username=:username")
    Optional<User> findByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM Users U WHERE U.id=:id")
    int delete(@Param("id") Long id);

    @Query("SELECT U FROM Users U WHERE U.email=:email")
    Optional<User>getUserByEmail(@Param("email") String email);

//    @EntityGraph(attributePaths = {"orders"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT U FROM Users U WHERE U.id=?1")
    Optional<User> getUserWithOrOrders(Long id);
}
