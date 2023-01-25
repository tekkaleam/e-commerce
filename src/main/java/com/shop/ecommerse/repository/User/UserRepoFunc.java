package com.shop.ecommerse.repository.User;

import com.shop.ecommerse.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepoFunc {

    User save(User user);

    // false if not found
    int delete(Long id);

    // null if not found
    Optional<User> get(Long id);

    // null if not found
    Optional<User> findByEmail(String email);

    List<User> getAll();

    Optional<User> findUserByUsername(String username);

    Optional<User> getUserWithOrders(Long id);

    Boolean existsByEmail(String email);

}
