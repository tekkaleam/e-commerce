package com.shop.ecommerse.repository;

import com.shop.ecommerse.domain.entity.PasswordForgotToken;
import com.shop.ecommerse.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordForgotTokenRepository extends JpaRepository<PasswordForgotToken, Long> {

    Optional<PasswordForgotToken> findByToken(String token);
    Optional<PasswordForgotToken> findByUser(User user);
}
