package com.shop.ecommerce.repository;

import com.shop.ecommerce.domain.entity.PasswordForgotToken;
import com.shop.ecommerce.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordForgotTokenRepository extends JpaRepository<PasswordForgotToken, Long> {

    Optional<PasswordForgotToken> findByToken(String token);
    Optional<PasswordForgotToken> findByUser(User user);
}
